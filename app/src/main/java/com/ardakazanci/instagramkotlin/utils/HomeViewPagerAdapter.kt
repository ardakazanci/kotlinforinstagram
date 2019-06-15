/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 10:12 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 10:12 PM
 *
 */

package com.ardakazanci.instagramkotlin.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /**
     * Tüm fragmentları tutacak ArrayList
     */
    private var myHomeFragmentList: ArrayList<Fragment> = ArrayList()


    /**
     * İndex'e göre ilgili fragmentın seçilmesini sağlar.
     * Zorunlu fonksiyon
     */
    override fun getItem(position: Int): Fragment {
        return myHomeFragmentList.get(position)
    }

    /**
     * Fragmentların sayısı
     * Zorunlu Fonksiyon
     */
    override fun getCount(): Int {
        return myHomeFragmentList.size
    }

    /**
     * Gönderilen fragment ArrayList'e eklenecek
     * Özelleştirilmiş fonksiyon
     */
    fun addHomeFragment(fragment: Fragment) {
        myHomeFragmentList.add(fragment)
    }

}