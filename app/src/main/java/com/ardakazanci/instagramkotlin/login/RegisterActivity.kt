/*
 * *
 *  * Created by Arda Kazancı on 6/18/19 8:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/18/19 8:33 PM
 *
 */

package com.ardakazanci.instagramkotlin.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        init()

    }

    private fun init() {


        textview_eposta.setOnClickListener {

            view_telefon.visibility = View.INVISIBLE
            view_eposta.visibility = View.VISIBLE
            edittext_register_type.setText("")
            edittext_register_type.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            edittext_register_type.hint = "E-Posta"


            // Aralarında yapılacak geçilerde görünüm sıfırlama işlemi

            btn_next.isEnabled = false
            btn_next.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.colorSonukMavi))
            //btn_next.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, R.color.beyaz))
            btn_next.setBackgroundResource(R.drawable.button_register)


        }


        /*textview_telefon.setOnClickListener {

            view_telefon.visibility = View.VISIBLE
            view_eposta.visibility = View.INVISIBLE
            edittext_register_type.setText("")
            edittext_register_type.inputType = InputType.TYPE_CLASS_NUMBER
            edittext_register_type.hint = "Telefon"

            // Aralarında yapılacak geçilerde görünüm sıfırlama işlemi

            btn_next.isEnabled = false
            btn_next.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.colorSonukMavi))
            //btn_next.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, R.color.beyaz))
            btn_next.setBackgroundResource(R.drawable.button_register)


        }*/

        // Interface olduğu için object tanımladık.
        edittext_register_type.addTextChangedListener(object : TextWatcher {


            // Edittext değişmeden sonra
            override fun afterTextChanged(p0: Editable?) {

            }

            // Edittext değişmeden önce
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


            // Edittext değişirken - değiştikçe
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {

                if (p1 + p2 + count >= 10) {

                    btn_next.isEnabled = true
                    btn_next.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.beyaz))
                    //btn_next.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, R.color.mavi))
                    btn_next.setBackgroundResource(R.drawable.button_register_active)
                } else {

                    btn_next.isEnabled = false
                    btn_next.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.colorSonukMavi))
                    //btn_next.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, R.color.beyaz))
                    btn_next.setBackgroundResource(R.drawable.button_register)

                }

            }


        })


        // İlgili Fragment ' ın kayıt yöntemine göre açılması
        // Root mantığıyla gizle göster tekniği uygulandı.


        btn_next.setOnClickListener {


            if (edittext_register_type.hint.toString().equals("Telefon")) {


                Toast.makeText(this@RegisterActivity,"Telefon Modülü Şimdilik Aktif Değil \n Lütfen E-Mail modülünü kullanınız",Toast.LENGTH_LONG).show()


                /*//Log.e("RegisterActivity","Telefon İleri tıklandı")

                constraint_login_root.visibility = View.GONE
                framelayout_login_root.visibility = View.VISIBLE

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.framelayout_login_root, TelephoneRegisterFragment())
                fragmentTransaction.addToBackStack("TelefonRegisterFragmentEklendi")
                fragmentTransaction.commit()
                // Gönderilecek Data
                // Butona tıklandığında yayın yapmaya başladı.
                // Ne zaman ki telefon fragment ' ı açılınca event ulaşacak
                // post'ta çalışmaz.
                EventBus.getDefault().postSticky(EventBusDataEvents.TelefonNoGonder(edittext_register_type.text.toString()))*/


            } else {

                //Log.e("RegisterActivity","E-Posta menüsü İleri tıklandı")

                if(isValidEmailControl(edittext_register_type.text.toString())){
                    constraint_login_root.visibility = View.GONE
                    framelayout_login_root.visibility = View.VISIBLE
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.framelayout_login_root, EmailRegisterFragment())
                    fragmentTransaction.addToBackStack("EmailRegisterFragmentEklendi")
                    fragmentTransaction.commit()
                    EventBus.getDefault().postSticky(EventBusDataEvents.EmailGonder(edittext_register_type.text.toString()))
                }else{
                    Toast.makeText(this@RegisterActivity,"Geçerli e-mail adresi giriniz.",Toast.LENGTH_LONG).show()
                }



            }


        }


    }


    override fun onBackPressed() {


        constraint_login_root.visibility = View.VISIBLE
        super.onBackPressed()
    }


    fun isValidEmailControl(kontrolEdilecekEmail:String) : Boolean {

        if(kontrolEdilecekEmail == null){
            return false
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(kontrolEdilecekEmail).matches()

    }




}
