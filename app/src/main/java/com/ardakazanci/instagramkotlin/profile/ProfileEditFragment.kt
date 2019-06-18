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
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import com.nostra13.universalimageloader.core.ImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*


class ProfileEditFragment : Fragment() {

    lateinit var circleProfilePictureImageView: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        circleProfilePictureImageView = v.findViewById(R.id.circleProfileImage)


        setupProfilePicture()


        v.imageview_profileedit_close.setOnClickListener {

            activity?.onBackPressed()

        }


        return v
    }



    private fun setupProfilePicture() {


        val imgUrl = "photo.isu.pub/ardakazanci/photo_large.jpg"
        UniversalImageLoader.setImage(imgUrl, circleProfilePictureImageView, null, "https://")


    }


}
