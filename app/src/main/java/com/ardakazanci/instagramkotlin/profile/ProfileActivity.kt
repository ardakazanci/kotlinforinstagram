/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:09 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 9:04 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.login.LoginActivity
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.greenrobot.eventbus.EventBus

class ProfileActivity : AppCompatActivity() {
    
    
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mUser: FirebaseUser
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    
    
    
    
        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser!!
        mRef = FirebaseDatabase.getInstance().reference
        
        
        setupToolbar()
    
        setupUserProfileInfo()
        setupBottomNavigationView()
    
    
    }
    
    /** Kullanıcı Bilgilerinin getirilmesini sağlayan metod */
    private fun setupUserProfileInfo() {
        // addValueEventListener değer değiştiğinde anlık olarak değişikliği algılar.
        
        mRef.child("users").child(mUser.uid).addValueEventListener(object : ValueEventListener {
            
            
            override fun onCancelled(p0: DatabaseError) {
            
            
            }
            
            override fun onDataChange(p0: DataSnapshot) {
                
                if (p0.getValue() != null) {
                    val readingUserInfo = p0.getValue(User::class.java)
                    
                    
                    // Kullanıcı bilgileri okunduktan sonra bir eventbus işlemi başlatıyoruz.
                    // postSticky denmesinin sebebi, yayın yapılırken, yayını dinleyecekler açılmamış olabilir.
                    EventBus.getDefault().postSticky(EventBusDataEvents.KullaniciBilgileriniGonder(readingUserInfo))
                    
                    
                    textview_username.text = readingUserInfo!!.userUserName
                    textview_personelname.text = readingUserInfo!!.userPersonelName
                    textview_content_shared_count.text = readingUserInfo.userDetails?.userPostCount
                    textview_follower_count.text = readingUserInfo.userDetails?.userFollowerCount
                    textview_followed_count.text = readingUserInfo.userDetails?.userFollowingCount
                    
                    
                    val profileImageUrl = readingUserInfo.userDetails?.userProfilePicture
                    
                    
                    
                    UniversalImageLoader.setImage(
                        profileImageUrl,
                        circleimageview_profile,
                        progressbar_profile_picture,
                        ""
                    )
                    
                    if (!readingUserInfo.userDetails?.userBiography.isNullOrEmpty()) {
                        
                        textview_biography.text = readingUserInfo.userDetails!!.userBiography!!
                        
                    }
                    
                    
                    if (!readingUserInfo.userDetails?.userWebsite.isNullOrEmpty()) {
                        
                        textview_biography.text = readingUserInfo.userDetails!!.userBiography!!
                        
                    }
                    
                    
                }
                
                
            }
            
            
        })
        
    }
    
    
    /** Toolbarın getirilmesini sağlayan metod*/
    private fun setupToolbar() {
        
        imageview_profile_options.setOnClickListener {
            val intent = Intent(
                this@ProfileActivity,
                ProfileSettingsActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        
        textview_profiledit.setOnClickListener {
            
            constraint_profilelayout_root.visibility = View.GONE
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout_profilelayout_root, ProfileEditFragment())
            transaction.addToBackStack("editProfileFragmentEklendi")
            transaction.commit()
            
        }
        
    }
    
    
    override fun onBackPressed() {
        constraint_profilelayout_root.visibility = View.VISIBLE
        super.onBackPressed()
    }
    
    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_profile)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(this@ProfileActivity, bottomnavigationview_profile)
        
        val menu = bottomnavigationview_profile.menu
        val menuItem = menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
    
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }
    
    
    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
    
    
    private fun setupAuthListener() {
        
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                
                
                val user = FirebaseAuth.getInstance().currentUser
                // Eğer auth olmadıysa yönlendirme yapıyoruz.
                if (user == null) {
                    
                    Log.e(TAG, "User Auth null")
                    
                    val intent =
                        Intent(
                            this@ProfileActivity,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish() // geri dönüldüğünde gelmemesi için.
                    
                    
                } else {
    
    
                    //Log.e("Çıkış Yapılamadı", " Null Değil")
                    
                    
                }
                
            }
            
        }
        
    }
    
    
    /**
     * Sabitler
     */
    companion object {
        const val TAG = "HomeActivity"
    
        /**
         * Seçili menünün iconunun seçili olmasını sağlamak için sabit.
         */
        const val ACTIVITY_NO = 4
    }
}
