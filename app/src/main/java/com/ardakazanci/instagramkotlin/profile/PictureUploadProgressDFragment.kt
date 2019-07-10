/*
 * *
 *  * Created by Arda Kazancı on 7/10/19 1:24 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/10/19 1:24 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.ardakazanci.instagramkotlin.R


class PictureUploadProgressDFragment : DialogFragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture_upload_progress_d, container, false)
    }
    
    
}
