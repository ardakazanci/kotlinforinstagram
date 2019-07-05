/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 8:01 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 8:01 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.circleimageview_profilesettings_profilepicture
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast


class ProfileEditFragment : Fragment() {
    
    lateinit var circleProfilePictureImageView: CircleImageView
    lateinit var getUserInfo: User
    
    lateinit var mDatabaseRef: DatabaseReference
    
    
    val ACTION_PICK = 1000
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    
    
        val v = inflater.inflate(R.layout.fragment_profile_edit, container, false)
    
    
        mDatabaseRef = FirebaseDatabase.getInstance().reference
        
        
        
        setupUserInfoEventListener(v)
    
    
    
        v.textview_profilesettings_picturechange.setOnClickListener {
        
            val intent = Intent()
            intent.setType("image/*") // Galeri açıldığında gösterilcek tür.
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(intent, ACTION_PICK)
        
        }
    
        v.imageview_profileedit_save.setOnClickListener {
        
            // Eğer ad-soyad değişmişse
        
            if (getUserInfo.userPersonelName!! != v.edittext_profilesettings_name.text.toString()) {
            
                // Kayıt işlemi
                mDatabaseRef.child("users").child(getUserInfo.userUID!!).child("userPersonelName")
                    .setValue(v.edittext_profilesettings_name.text.toString())
            
            }
        
        
            // Eğer Biyografi değişmişse
        
            if (getUserInfo.userDetails!!.userBiography != v.edittext_profilesettings_biography.text.toString()) {
            
                // Kayıt işlemi
                mDatabaseRef.child("users").child(getUserInfo.userUID!!).child("userDetails").child("userBiography")
                    .setValue(v.edittext_profilesettings_biography.text.toString())
            
            }
        
        
            // Eğer websayfası bilgisi değişmişse veritabanına kaydetme isteği gönder.
        
            if (getUserInfo.userDetails!!.userWebsite != v.edittext_profilesettings_website.text.toString()) {
            
                // Kayıt işlemi
                mDatabaseRef.child("users").child(getUserInfo.userUID!!).child("userDetails").child("userWebsite")
                    .setValue(v.edittext_profilesettings_website.text.toString())
            
            }
        
        
            if (getUserInfo.userUserName != v.edittext_profilesettings_username.text.toString()) {
            
                mDatabaseRef.child("users").orderByChild("userUserName")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        
                        }
                    
                    
                        override fun onDataChange(p0: DataSnapshot) {
                        
                            var userNameUsedControl = false
                        
                        
                            for (ds in p0.children) {
                            
                                val readingUserNameControl = ds!!.getValue(User::class.java)!!.userUserName
                            
                            
                                if (readingUserNameControl!! == v.edittext_profilesettings_username.text.toString()) {
                                
                                    Toast.makeText(activity, "Kullanıcı adı kullanımda", Toast.LENGTH_LONG).show()
                                    userNameUsedControl = true
                                    break
                                
                                }
                            
                            
                            }
                        
                        
                            if (!userNameUsedControl) {
                            
                                mDatabaseRef.child("users").child(getUserInfo.userUID!!).child("userUserName")
                                    .setValue(v.edittext_profilesettings_username.text.toString())
                            }
                        
                        
                        }
                    
                    
                    })
            
            }
        
            // İF BLOGU SON
        
            //Toast.makeText(activity, "Kullanıcı bilgileri güncellendi", Toast.LENGTH_LONG).show()
        
        
        
        }
        
        
        v.imageview_profileedit_close.setOnClickListener {
    
            activity?.onBackPressed()
    
        }
    
    
        return v
    }
    
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == ACTION_PICK && resultCode == AppCompatActivity.RESULT_OK && data!!.data != null) {
            
            
            val userImageUri = data.data
            
            circleimageview_profilesettings_profilepicture.setImageURI(userImageUri)
            
        }
    }
    
    
    /**
     * EventBus ile dinlediğimiz dataları burada işleyeceğiz.
     * v: View elemanı aracılığıyla iç view elemanlarına erişmek istiyoruz.
     */
    private fun setupUserInfoEventListener(v: View?) {
        
        
        
        v!!.edittext_profilesettings_name.setText(getUserInfo.userPersonelName)
        v.edittext_profilesettings_username.setText(getUserInfo.userUserName)
        
        
        //v.circleimageview_profilesettings_profilepicture.
        
        val userProfilePicture = getUserInfo.userDetails?.userProfilePicture
        
        UniversalImageLoader.setImage(
            userProfilePicture,
            v.circleimageview_profilesettings_profilepicture,
            v.progressbar_profilesettings_pictureloading,
            ""
        )
        
        
        
        // isNullOrEmpty kontrolü sağlanabilir fakat ? işareti ile aynı süreci işletmiş olduk. Null olabilir gibi...
        v.edittext_profilesettings_biography.setText(getUserInfo.userDetails?.userBiography)
        v.edittext_profilesettings_website.setText(getUserInfo.userDetails?.userWebsite)
        
        
    }
    
    /**
     * ///////////// EVENTBUS İŞLEMLERİ   /////////////
     */
    @Subscribe(sticky = true)
    internal fun onUserInfoEvent(userInfos: EventBusDataEvents.KullaniciBilgileriniGonder) {
        
        getUserInfo = userInfos.user!!
    
    }
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }
    
    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
    
    
}
