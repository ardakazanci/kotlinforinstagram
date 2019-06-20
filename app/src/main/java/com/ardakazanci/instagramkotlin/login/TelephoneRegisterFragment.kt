/*
 * *
 *  * Created by Arda Kazancı on 6/19/19 8:31 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/19/19 8:31 PM
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
import android.widget.Toast

import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_telephone_code_control.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit


class TelephoneRegisterFragment : Fragment() {

    // Fiziksel cihaz olması gerekiyor.


    var gelenTelefonNumara = ""
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationID = ""
    var gelenKod = ""


    val phoneNum = "+905061609320"
    val testVerificationCode = "123456"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_telephone_code_control, container, false)


        v.textview_telephone_view.text = gelenTelefonNumara


        setupPhoneCallback()

        v.button_telephone_next.setOnClickListener {


            //Log.e("Girilen Kod -> ", v.edittext_telephone_code.text.toString())

            if (gelenKod.equals(v.edittext_telephone_code.text.toString())) {



            } else {



            }

        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNum,
            60,
            TimeUnit.SECONDS,
            this.activity!!,
            callbacks
        )





        return v
    }


    private fun setupPhoneCallback() {


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {


                gelenKod = credential.smsCode!!
                Log.e("Gelen Kod -> ", gelenKod)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.e("Firebase Phone", e.message)


            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                verificationID = verificationId!!


            }

        }
    }


    @Subscribe(sticky = true)
    internal fun onTelefonNoEvent(telNo: EventBusDataEvents.TelefonNoGonder) {


        gelenTelefonNumara = telNo.telNo
        Log.e("Gelen Telefon No:", gelenTelefonNumara)


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
