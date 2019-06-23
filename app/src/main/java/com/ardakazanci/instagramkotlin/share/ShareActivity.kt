/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:09 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 9:02 PM
 *
 */

package com.ardakazanci.instagramkotlin.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.login.LoginActivity
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class ShareActivity : AppCompatActivity() {
    
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    
    
    
        mAuth = FirebaseAuth.getInstance()
        setupAuthListener()
        setupBottomNavigationView()
    
    }
    
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }
    
    
    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
    
    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_main)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(this@ShareActivity, bottomnavigationview_main)
    
        val menu = bottomnavigationview_main.menu
        val menuItem = menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
    
    
    private fun setupAuthListener() {
        
        mAuthListener = FirebaseAuth.AuthStateListener {
            
            val user = FirebaseAuth.getInstance().currentUser
            // Eğer auth olmadıysa yönlendirme yapıyoruz.
            if (user == null) {
                
                val intent =
                    Intent(
                        this@ShareActivity,
                        LoginActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish() // geri dönüldüğünde gelmemesi için.
                
                
            } else {
            
            }
        }
        
    }
    
    
    /**
     * Sabitler
     */
    companion object {
        const val TAG = "ShareActivity"
    
        /**
         * Seçili menünün iconunun seçili olmasını sağlamak için sabit.
         */
        const val ACTIVITY_NO = 2
    }
}
