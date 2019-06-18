/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 8:01 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 8:01 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.instagramkotlin.R


class SignOutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_out, container, false)
    }


}
