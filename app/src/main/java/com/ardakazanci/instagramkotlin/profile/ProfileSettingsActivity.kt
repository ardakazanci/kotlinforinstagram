/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 6:56 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 6:56 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.news.NewsActivity
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_profile_settings.*

class ProfileSettingsActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupBottomNavigationView()
    }

    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_profile_settings)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(this@ProfileSettingsActivity, bottomnavigationview_profile_settings)

        val menu = bottomnavigationview_profile_settings.menu
        val menuItem = menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    companion object {
        const val TAG = "ProfileSettingsActivity"

        /**
         * Seçili menünün iconunun seçili olmasını sağlamak için sabit.
         */
        const val ACTIVITY_NO = 4
    }
}
