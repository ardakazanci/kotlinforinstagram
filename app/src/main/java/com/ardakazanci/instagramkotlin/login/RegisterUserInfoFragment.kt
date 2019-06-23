/*
 * *
 *  * Created by Arda Kazancı on 6/19/19 8:37 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/19/19 8:37 PM
 *
 */

package com.ardakazanci.instagramkotlin.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import org.jetbrains.anko.toast
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_kayit.*
import kotlinx.android.synthetic.main.fragment_kayit.view.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class EmailRegisterFragment : Fragment() {
    /**
     * edittext_kayit_adsoyad
     * edittext_kayit_parola
     * edittext_kayit_kullaniciadi
     * button_kayit_aksiyon
     */

    /**
     * ///////////// GLOBAL DEĞERLER /////////////
     */
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    var getUserEmailAdress = ""
    lateinit var progressbar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_kayit, container, false)

        /**
         * ///////////// GLOBAL DEĞERLERİN TANIMLANMASI /////////////
         */

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        progressbar = v.progressbar_register_button_clicked
        progressbar.visibility = View.INVISIBLE
        v.edittext_kayit_adsoyad.addTextChangedListener(watcher)
        v.edittext_kayit_parola.addTextChangedListener(watcher)
        v.edittext_kayit_kullaniciadi.addTextChangedListener(watcher)


        v.tvGirisYap.setOnClickListener {

            val intent =
                Intent(activity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)


        }


        /**
         * ///////////// EMAİL İLE İLERİ BUTONUNA TIKLANDIĞINDA YAPILACAK İŞLEMLER /////////////
         */

        v.button_kayit_aksiyon.setOnClickListener {


            /**
             * ///// USERNAME KULLANIMDA MI DEĞİL Mİ KONTROLÜ
             */
            var userNameDbCheckControl = false


            mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0.value != null) {

                        for (user in p0.children) {

                            val readingUserNameInfo = user.getValue(User::class.java)

                            if (readingUserNameInfo?.userUserName.equals(v.edittext_kayit_kullaniciadi.text.toString())) {

                                activity!!.toast("Kullanıcı adı kullanımdadır.")
                                userNameDbCheckControl = true
                                break

                            }

                        }


                        if (userNameDbCheckControl == false) {

                            progressbar.visibility = View.VISIBLE
                            val userPassword = v.edittext_kayit_parola.text.toString()
                            val userUserName = v.edittext_kayit_kullaniciadi.text.toString()
                            val userUserPersonelName = v.edittext_kayit_adsoyad.text.toString()

                            /**
                             * ///////////// OTURUM AÇMA İŞLEMLERİ /////////////
                             */

                            mAuth.createUserWithEmailAndPassword(getUserEmailAdress, userPassword)
                                .addOnCompleteListener { p0 ->
                                    if (p0.isSuccessful) {
                                        //activity?.toast("Auth İşlemi Başarılı")
                                        val userUId =
                                            mAuth.currentUser!!.uid.toString() // Oturum açıldıktan sonra aldık.
                                        val registerUser =
                                            User(
                                                "",
                                                userUId,
                                                getUserEmailAdress,
                                                userPassword,
                                                userUserName,
                                                userUserPersonelName
                                            )


                                        /**
                                         * /////////////  VERİTABANINA KAYIT ALMA İŞLEMLERİ  /////////////
                                         */

                                        mRef.child("users")
                                            .child(userUId).setValue(registerUser)
                                            .addOnCompleteListener { p0 ->
                                                if (p0.isSuccessful) {
                                                    activity?.toast("Kayıt Başarılı")
                                                    progressbar.visibility = View.INVISIBLE
                                                } else {
                                                    ///////////// AYNI EMAİL İLE KAYIT ALINDIĞINDA YAPILACAK İŞLEMLER /////////////
                                                    mAuth.currentUser!!.delete().addOnCompleteListener { p0 ->
                                                        if (p0.isSuccessful) {

                                                            activity?.toast("Kullanıcı Kaydedilemedi")

                                                        } else {

                                                            progressbar.visibility = View.INVISIBLE
                                                            Log.e("Silme", "Başarısız")
                                                        }
                                                    }
                                                    progressbar.visibility = View.INVISIBLE
                                                    Log.e("Kayıt", "Başarısız" + p0.exception.toString())
                                                }
                                            }
                                    } else {
                                        progressbar.visibility = View.INVISIBLE
                                        Log.e("Auth", "Başarısız" + p0.exception.toString())
                                    }
                                }

                        }


                    }

                }


            })


        }

        return v
    }


    /**
     * ///////////// TEXTWATCHER İŞLEMLERİ   /////////////
     */
    var watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (s!!.length > 5) {
                if (edittext_kayit_adsoyad.text.toString().length > 5 &&
                    edittext_kayit_parola.text.toString().length > 5 &&
                    edittext_kayit_kullaniciadi.text.toString().length > 5
                ) {
                    button_kayit_aksiyon.isEnabled = true
                    button_kayit_aksiyon.setTextColor(ContextCompat.getColor(activity!!, R.color.beyaz))
                    button_kayit_aksiyon.setBackgroundResource(R.drawable.button_register_active)
                } else {
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


    /**
     * ///////////// EVENTBUS İŞLEMLERİ   /////////////
     */
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
