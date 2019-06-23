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
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    
    
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    
    
    
        mAuth = FirebaseAuth.getInstance()
        setupAuthListener()
       
        
        setupToolbar()
        setupProfilePicture()
        setupBottomNavigationView()
    
    
    }

    /**
     * Profil resmi eklenmektedir.
     */
    private fun setupProfilePicture() {
    
        val pictureUrl = "gelecegiyazanlar.turkcell.com.tr/sites/default/files/pictures/picture-69869-1547309997.jpg"
        UniversalImageLoader.setImage(pictureUrl, circleimageview_profile, progressbar_profile_picture, "https://")

    }

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
            transaction.replace(R.id.framelayout_profilelayout_root,ProfileEditFragment())
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
                    
                    
                    Log.e("Çıkış Yapılamadı", " Null Değil")
                    
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
