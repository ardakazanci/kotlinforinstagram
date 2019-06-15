/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 9:09 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 9:03 PM
 *
 */

package com.ardakazanci.instagramkotlin.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_home.*

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        setupBottomNavigationView()

    }

    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_main)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(this@NewsActivity, bottomnavigationview_main)

        val menu = bottomnavigationview_main.menu
        val menuItem = menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }



    /**
     * Sabitler
     */
    companion object {
        const val TAG = "HomeActivity"

        /**
         * Seçili menünün iconunun seçili olmasını sağlamak için sabit.
         */
        const val ACTIVITY_NO = 3
    }
}
