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
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.home.HomeActivity
import com.ardakazanci.instagramkotlin.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    
    /**
     * Auth ve Veritabanı işlemleri için erken yükleme yaptık.
     */
    
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
    
    private fun setupAuthListener() {
        
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                
                //
                val user = FirebaseAuth.getInstance().currentUser
                
                if (user != null) {
                    
                    val intent =
                        Intent(this@LoginActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish() // geri dönüldüğünde gelmemesi için.
                    
                    
                } else {
                
                }
                
            }
            
        }
        
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    
    
    
    
        setupAuthListener()
        
        mAuth = FirebaseAuth.getInstance()
        //mAuth.signOut() test aşamasında oturum açtıysak çıkış yaptırmak için.
        mRef = FirebaseDatabase.getInstance().reference
        init()
    
        /**
         * PRATİK GEÇİŞ BUTONU OLARKA TASARLANAN KESTİRME REGİSTER BUTONU TANIMLANMASI
         *
         */
    
    
    
    }
    
    
    
    
    /**
     * KULLANICI ARAYÜZ BİLGİLERİNİN TANIMLANMASI
     */
    private fun init() {
    
        // edittext_login_email_info
        // edittext_login_password_info
        // button_login
    
        textview_register.setOnClickListener {
        
            val intent =
                Intent(this@LoginActivity, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        
        
        }
        
        edittext_login_email_info.addTextChangedListener(watcher)
        edittext_login_password_info.addTextChangedListener(watcher)
    
    
        button_login.setOnClickListener {
        
        
            loginControl(edittext_login_email_info.text.toString(), edittext_login_password_info.text.toString())
        
        }
    
    
    }
    
    
    /**
     * GİRİLEN BİLGİLERİN VERİTABANINDA USEREMAİL OLARAK VAR MI YOK MU KONTROLÜNÜN SAĞLANMASI
     * DAHA SONRA AUTH İÇİN PAROLA KONTROLÜNE GEÇİRİLMESİ
     * 2 METOTTA OTORUM AÇMA İŞLEMİ GERÇKELŞTİRİYORO
     */
    private fun loginControl(userLoginInfoUserNameOrUserEmail: String, userPassword: String) {
        
        // Users düğümünü dinliyoruz.
        mRef.child("users").orderByChild("userEmail")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
    
                }
    
                override fun onDataChange(p0: DataSnapshot) {
        
        
                    for (userInfo in p0.children) {
            
                        val readingUserInfo = userInfo.getValue(User::class.java)
            
                        if (readingUserInfo?.userEmail.equals(userLoginInfoUserNameOrUserEmail)) {
                
                            // Giriş Bilgileri veritabanında eşlendiyse Mimari gereği password u de göndermemiz gerek.
                            userAuthentication(readingUserInfo, userPassword)
                            break
                
                
                        } else if (readingUserInfo?.userUserName.equals(userLoginInfoUserNameOrUserEmail)) {
                
                            userAuthentication(readingUserInfo, userPassword)
                            break
                
                
                        } else {
                
                            Toast.makeText(
                                this@LoginActivity,
                                "Kullanıcı adı veya Email adresi eşleşmiyor.",
                                Toast.LENGTH_LONG
                            ).show()
                            
                        }
            
            
                    }
        
        
                }
    
    
            })
        
        
    }
    
    // Diğer tekniklerde Telefon yada kullanıcı adı gibi kontroller ile de giriş yapılmış olabilir.
    private fun userAuthentication(readingUserInfo: User?, userPassword: String) {
        
        
        val loggedUserEmailAdress = readingUserInfo?.userEmail.toString()
        
        
        // Girilen Kullanıcı adı bilgileri veritabanında uyuşuyorsa auth açılıyor. s
        mAuth.signInWithEmailAndPassword(loggedUserEmailAdress, userPassword)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
    
                    if (p0.isSuccessful) {
        
                        Toast.makeText(this@LoginActivity, "Oturum Açıldı", Toast.LENGTH_LONG).show()
        
        
                    } else {
        
                        Toast.makeText(this@LoginActivity, "Kullanıcı Adı yada Parola Hatalı", Toast.LENGTH_LONG).show()
                        
                        
                    }
    
                }
    
    
            })
        
        
    }
    
    /**
     * LOGIN GİRİŞ BİLGİLERİNİN KONTROLÜ - TEXTWATCHER
     */
    var watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
    
        }
        
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        
        }
        
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            
            if (edittext_login_email_info.text.toString().length >= 6 && edittext_login_password_info.text.toString().length >= 6) {
                
                button_login.isEnabled = true
                button_login.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.beyaz))
                button_login.setBackgroundResource(R.drawable.button_register_active)
                
                
            } else {
                
                button_login.isEnabled = false
                button_login.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSonukMavi))
                button_login.setBackgroundResource(R.drawable.button_register)
                
                
            }
            
        }
        
    }
    
    
}
