/*
 * *
 *  * Created by Arda Kazancı on 6/19/19 8:37 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/19/19 8:37 PM
 *
 */

package com.ardakazanci.instagramkotlin.login


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EmailRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_kayit, container, false)



        return view

    }






    @Subscribe(sticky = true)
    internal fun onEmailNoEvent(emailAdres:EventBusDataEvents.EmailGonder){

        val gelenEmailAdres = emailAdres.email
        Log.e("Gelen Telefon No:", gelenEmailAdres)


    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }


}
