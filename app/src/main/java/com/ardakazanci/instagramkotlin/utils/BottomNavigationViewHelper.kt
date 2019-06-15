/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:09 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 8:54 PM
 *
 */

package com.ardakazanci.instagramkotlin.utils

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.home.HomeActivity
import com.ardakazanci.instagramkotlin.news.NewsActivity
import com.ardakazanci.instagramkotlin.profile.ProfileActivity
import com.ardakazanci.instagramkotlin.search.SearchActivity
import com.ardakazanci.instagramkotlin.share.ShareActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class BottomNavigationViewHelper {


    companion object {


        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {


            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.setTextVisibility(false)
            bottomNavigationViewEx.enableItemShiftingMode(false)
            bottomNavigationViewEx.enableShiftingMode(false)


        }

        /**
         * Her bir menü tıklandığında tetiklenecek activityler.
         */
        fun setupBottomNavigationViewClicked(context: Context, bottomNavigationViewEx: BottomNavigationViewEx) {


            bottomNavigationViewEx.onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {


                    when (menuItem.itemId) {


                        R.id.menu_tab_home -> {


                            val intent = Intent(context, HomeActivity::class.java)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.menu_tab_news -> {
                            val intent = Intent(context, NewsActivity::class.java)
                            context.startActivity(intent)
                            return true

                        }

                        R.id.menu_tab_profile -> {

                            val intent = Intent(context, ProfileActivity::class.java)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.menu_tab_search -> {

                            val intent = Intent(context, SearchActivity::class.java)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.menu_tab_share -> {

                            val intent = Intent(context, ShareActivity::class.java)
                            context.startActivity(intent)
                            return true
                        }

                        else ->{
                            return false
                        }


                    }


                }


            }


        }


    }

}