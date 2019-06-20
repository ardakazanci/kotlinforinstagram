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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_kayit.*
import kotlinx.android.synthetic.main.fragment_kayit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class EmailRegisterFragment : Fragment() {


    /**
     * edittext_kayit_adsoyad
     * edittext_kayit_parola
     * edittext_kayit_kullaniciadi
     * button_kayit_aksiyon
     */

    lateinit var mAuth:FirebaseAuth
    lateinit var mRef:DatabaseReference
    var getUserEmailAdress = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_kayit, container, false)

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference


        v.edittext_kayit_adsoyad.addTextChangedListener(watcher)
        v.edittext_kayit_parola.addTextChangedListener(watcher)
        v.edittext_kayit_kullaniciadi.addTextChangedListener(watcher)

        v.button_kayit_aksiyon.setOnClickListener {




            val userPassword = v.edittext_kayit_parola.text.toString()
            val userUserName = v.edittext_kayit_kullaniciadi.text.toString()
            val userUserPersonelName = v.edittext_kayit_adsoyad.text.toString()

            // Email ile kayıt alınıyor.
            mAuth.createUserWithEmailAndPassword(getUserEmailAdress,userPassword)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                    override fun onComplete(p0: Task<AuthResult>) {

                        if(p0.isSuccessful){
                            Log.e("Auth","Başarılı")

                            // OTURUM AÇAN KULLANICININ VERİLERİNİN VERİTABANINA KAYDEDİLMESİ.
                            val userUId = mAuth.currentUser!!.uid.toString() // Oturum açıldıktan sonra aldık.
                            val registerUser = User(userUId,getUserEmailAdress,userPassword,userUserName,userUserPersonelName )

                            mRef.child("users")
                                .child("useruid").setValue(registerUser)
                                .addOnCompleteListener(object : OnCompleteListener<Void>{
                                    override fun onComplete(p0: Task<Void>) {

                                        if(p0!!.isSuccessful){

                                            Log.e("Kayıt","Kayıt Başarılı")

                                        }else{
                                            Log.e("Kayıt","Başarısız"+ p0.exception.toString())
                                        }

                                    }


                                })



                        }else{
                            Log.e("Auth","Başarısız"+p0.exception.toString())
                        }

                    }


                })

        }

        return v

    }






    var watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {


            if (s!!.length > 5) {


                if(edittext_kayit_adsoyad.text.toString().length > 5 &&
                    edittext_kayit_parola.text.toString().length > 5 &&
                    edittext_kayit_kullaniciadi.text.toString().length > 5
                    ){


                    button_kayit_aksiyon.isEnabled = true
                    button_kayit_aksiyon.setTextColor(ContextCompat.getColor(activity!!, R.color.beyaz))
                    button_kayit_aksiyon.setBackgroundResource(R.drawable.button_register_active)


                }else{

                    button_kayit_aksiyon.isEnabled = false
                    button_kayit_aksiyon.setTextColor(ContextCompat.getColor(activity!!, R.color.colorSonukMavi))
                    button_kayit_aksiyon.setBackgroundResource(R.drawable.button_register)

                }


            } else {

                button_kayit_aksiyon.isEnabled = false
                button_kayit_aksiyon.setTextColor(ContextCompat.getColor(activity!!, R.color.colorSonukMavi))
                button_kayit_aksiyon.setBackgroundResource(R.drawable.button_register)

            }


        }


    }


    ///////////// EVENTBUS İŞLEMLERİ /////////////

    @Subscribe(sticky = true)
    internal fun onEmailNoEvent(emailAdres: EventBusDataEvents.EmailGonder) {

        getUserEmailAdress = emailAdres.email
        Log.e("Gelen E-Mail Adres:", getUserEmailAdress)


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
