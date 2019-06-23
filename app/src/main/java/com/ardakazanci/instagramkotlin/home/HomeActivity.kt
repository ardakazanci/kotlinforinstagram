/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:08 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 9:08 PM
 *
 */

package com.ardakazanci.instagramkotlin.home


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.login.LoginActivity
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import com.ardakazanci.instagramkotlin.utils.HomeViewPagerAdapter
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    
    
    
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    
    
    
    
        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        initUniversalImageLoader()
        setupBottomNavigationView()
        setupHomeViewPager()

    }

    /**
     * HomeActivity içerisinde yer alan ViewPager işlemleri
     */
    private fun setupHomeViewPager() {
        val homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        // Sırasıyla gösterilecek Fragmentlar
        homeViewPagerAdapter.addHomeFragment(CameraFragment()) // id 0
        homeViewPagerAdapter.addHomeFragment(HomeFragment())  // id 1
        homeViewPagerAdapter.addHomeFragment(MessagesFragment()) // id 2

        // ViewPager 'ı adapter ü ile bağlıyoruz.
        viewpager_main.adapter = homeViewPagerAdapter

        viewpager_main.setCurrentItem(1) // .currentItem = 1 // setCurrentItem(1)
    }


    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_main)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(this@HomeActivity, bottomnavigationview_main)

        val menu = bottomnavigationview_main.menu
        val menuItem = menu.getItem(ACTIVITY_NO)
        menuItem.isChecked = true
    }


    /**
     * UniversalImageLoader 'ı 1 kere tetiklememiz gerekiyor.
     * Launcher activity(HomeActivity) de bir kere çağırmamız yeterli oldu.
     * Context: this
     */
    private fun initUniversalImageLoader() {
        // Fragment içerisinde olduğumuz için context bağlı olduğu activtiy i gönderebiliriz.
        val universalImageLoader = UniversalImageLoader(this@HomeActivity)
        ImageLoader.getInstance().init(universalImageLoader.config)

    }
    
    
    private fun setupAuthListener() {
        
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                
                //
                val user = FirebaseAuth.getInstance().currentUser
                // Eğer auth olmadıysa yönlendirme yapıyoruz.
                if (user == null) {
                    
                    val intent =
                        Intent(
                            this@HomeActivity,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish() // geri dönüldüğünde gelmemesi için.
                    
                    
                } else {
                
                }
                
            }
            
        }
        
    }
    
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }
    
    
    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
    

    /**
     * Sabitler
     */
    companion object {
        const val TAG = "HomeActivity"

        /**
         * Seçili menünün iconunun seçili olmasını sağlamak için sabit.
         */
        const val ACTIVITY_NO = 0
    }
}
