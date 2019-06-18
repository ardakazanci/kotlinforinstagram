/*
 * *
 *  * Created by Arda Kazancı on 6/18/19 3:24 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/18/19 3:24 PM
 *
 */

package com.ardakazanci.instagramkotlin.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.ardakazanci.instagramkotlin.R
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

class UniversalImageLoader(val context: Context) {


    val config: ImageLoaderConfiguration
        get() {

            val defaultOptions = DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(FadeInBitmapDisplayer(400))
                .build()

            return ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build()

        }


    companion object {

        private val defaultImage = R.drawable.ic_tab_profile

        fun setImage(imageUrl: String, imageView: ImageView, mProgressBar: ProgressBar?, ilkKisim: String?) {


            val imageLoader = ImageLoader.getInstance()

            imageLoader.displayImage(ilkKisim + imageUrl, imageView, object : ImageLoadingListener {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {

                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }


                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {

                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.VISIBLE
                    }

                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {

                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }

                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {

                    if (mProgressBar != null) {
                        mProgressBar.visibility = View.GONE
                    }

                }


            })

        }

    }

}