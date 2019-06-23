/*
 * *
 *  * Created by Arda Kazancı on 6/18/19 8:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/18/19 8:33 PM
 *
 */

package com.ardakazanci.instagramkotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.home.HomeActivity
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {
    
    
    lateinit var mRef: DatabaseReference
    
    
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    
    
    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }
    
    
    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
    
    
    /**
     * Oturum açıldıysa , kullanıcı çıkışının sağlanmasını ele alır.
     * Listener kullanıcı oturumda mı değil mi kontrolü sağlıyoruz.
     */
    private fun setupAuthListener() {
        
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                
                //
                val user = FirebaseAuth.getInstance().currentUser
                
                if (user != null) {
                    
                    val intent =
                        Intent(
                            this@RegisterActivity,
                            HomeActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish() // geri dönüldüğünde gelmemesi için.
                    
                    
                } else {
                
                }
                
            }
            
        }
        
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupAuthListener()
        mRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        init()
    
    }
    
    private fun init() {
        
        
        textview_login.setOnClickListener {
            
            val intent =
                Intent(this@RegisterActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
            
        }
        
        
        textview_eposta.setOnClickListener {
            
            view_telefon.visibility = View.INVISIBLE
            view_eposta.visibility = View.VISIBLE
            edittext_register_type.setText("")
            edittext_register_type.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            edittext_register_type.hint = "E-Posta"
            
            
            // Aralarında yapılacak geçişlerde görünüm sıfırlama işlemi
            
            btn_next.isEnabled = false
            btn_next.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.colorSonukMavi))
            btn_next.setBackgroundResource(R.drawable.button_register)
            
            
        }
        
        
        /*

            TELEFON TAB MENÜSÜ PASİF HALE GETİRİLDİ. DAHA SONRA FİZİKSEL CİHAZ KULLANIMINDA ELE ALINACAKTIR.

           textview_telefon.setOnClickListener {

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
        
        // Interface olduğu için object inner class tanımladık.
        
        
        /**
         * EDİTTEXT E GİRİLEN DEĞERİN KARAKTER SAYISI KONTROLÜ İÇİN KULLANILDI
         */
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
        
        
        /**
         *  İlgili Fragment ' ın kayıt yöntemine göre açılması
         *  Root mantığıyla gizle göster tekniği uygulandı.
         */
        
        btn_next.setOnClickListener {
            
            
            if (edittext_register_type.hint.toString().equals("Telefon")) {
                
                
                Toast.makeText(
                    this@RegisterActivity,
                    "Telefon Modülü Şimdilik Aktif Değil \n Lütfen E-Mail modülünü kullanınız",
                    Toast.LENGTH_LONG
                ).show()
                
                
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
                
                
                /**
                 * EMAIL GEÇERLİLİK KONTROLÜ
                 */
                
                if (isValidEmailControl(edittext_register_type.text.toString())) {
                    
                    
                    var emailDbControl = false
                    /**
                     * EMAIL BİLGİLERİ KULLANIMDA MI DEĞİL Mİ KONTROLÜ
                     */
                    mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
    
                        }
    
                        override fun onDataChange(p0: DataSnapshot) {
        
                            if (p0.value != null) {
            
            
                                for (user in p0.children) {
                
                                    val readingUserInfo = user.getValue(User::class.java)
                
                                    if (readingUserInfo?.userEmail.equals(edittext_register_type.text.toString())) {
                    
                                        emailDbControl = true // Kullanımda
                    
                                        toast("E-Mail Kullanımda")
                                        break
                    
                                    }
                
                
                                }
            
                                /**
                                 * EMAIL KULLANIMDA DEĞİLSE KULLANICI BİLGİLERİNİN İSTENDİĞİ EKRANA GEÇİŞ YAPILIYOR.
                                 */
                                if (emailDbControl == false) {
    
                                    constraint_login_root.visibility = View.GONE
                                    framelayout_login_root.visibility = View.VISIBLE
                                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                                    fragmentTransaction.replace(R.id.framelayout_login_root, EmailRegisterFragment())
                                    fragmentTransaction.addToBackStack("EmailRegisterFragmentEklendi")
                                    fragmentTransaction.commit()
                                    EventBus.getDefault()
                                        .postSticky(EventBusDataEvents.EmailGonder(edittext_register_type.text.toString()))
    
    
                                }
            
            
                            }
        
                        }
    
    
                    })
                    
                    
                } else {
                    Toast.makeText(this@RegisterActivity, "Geçerli e-mail adresi giriniz.", Toast.LENGTH_LONG).show()
                }
                
                
            }
            
            
        }
        
        
    }
    
    /**
     * GERİ BUTONUNA BASILDIĞINDA TEKRAR GÖSTERİLMESİ İÇİN.
     */
    override fun onBackPressed() {
        
        constraint_login_root.visibility = View.VISIBLE
        super.onBackPressed()
    }
    
    /**
     * EMAIL GEÇERLİLİK PATTERN I
     */
    fun isValidEmailControl(kontrolEdilecekEmail: String): Boolean {
        
        return android.util.Patterns.EMAIL_ADDRESS.matcher(kontrolEdilecekEmail).matches()
        
    }
    
    
}
