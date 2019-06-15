/*
 * *
 *  * Created by Arda Kazancı on 6/15/19 10:06 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/15/19 10:06 PM
 *
 */

package com.ardakazanci.instagramkotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardakazanci.instagramkotlin.R

class MessagesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        return view
    }

}