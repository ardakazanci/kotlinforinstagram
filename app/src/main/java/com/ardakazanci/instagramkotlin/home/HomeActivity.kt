/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:08 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 9:08 PM
 *
 */

package com.ardakazanci.instagramkotlin.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import com.ardakazanci.instagramkotlin.utils.HomeViewPagerAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
