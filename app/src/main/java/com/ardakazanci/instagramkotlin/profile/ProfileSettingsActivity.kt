/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 6:56 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 6:56 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.news.NewsActivity
import com.ardakazanci.instagramkotlin.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_profile_settings.*

class ProfileSettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupBottomNavigationView()
        setupToolbar()
        fragmentNavigations()
    }


    /**
     * SettingsActivity içerisinde yer alan menüler için özel Fragment gösterilecek
     * Bu işlemi bu metot aracılığıyla geçişlerini ele alacağız.
     */
    private fun fragmentNavigations() {

        textview_accounts_settings_profile_edit.setOnClickListener {
            constraintlayout_settings_profile_root.visibility = View.GONE
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout_settings_profile_root,ProfileEditFragment())
            transaction.addToBackStack("ProfileEditFragmentEklendi")
            transaction.commit()


        }

        textview_accounts_settings_sign_out.setOnClickListener {
            constraintlayout_settings_profile_root.visibility = View.GONE
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.constraintlayout_settings_profile_root,SignOutFragment())
            transaction.addToBackStack("signoutFragmentEklendi")
            transaction.commit()


        }



    }

    /**
     * Fragment açılışında tıklanıldığında GONE yapılan root layout
     * Geri butonuna tıklandığında tekrar gösterime geliyor.
     */
    override fun onBackPressed() {
        constraintlayout_settings_profile_root.visibility = View.VISIBLE
        super.onBackPressed()



    }

    private fun setupToolbar() {

        imageview_profile_settings_back_button.setOnClickListener {

            onBackPressed()
        }

    }

    fun setupBottomNavigationView() {
        BottomNavigationViewHelper.setupBottomNavigationView(bottomnavigationview_profile_settings)
        BottomNavigationViewHelper.setupBottomNavigationViewClicked(
            this@ProfileSettingsActivity,
            bottomnavigationview_profile_settings
        )

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
