/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 8:01 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 8:01 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.ardakazanci.instagramkotlin.R
import com.google.firebase.auth.FirebaseAuth


class SignOutFragment : DialogFragment() {
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        
        val alert = AlertDialog.Builder(this.activity)
            .setTitle("Instagram Çıkış Yap")
            .setMessage("Emin misiniz?")
            .setPositiveButton("Çıkış Yap", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    
                    
                    FirebaseAuth.getInstance().signOut()
                    activity?.finish() // Çıkış yaptıktan sonra kapanacak.
                    
                    
                }
                
                
            })
            
            .setNegativeButton("İptal", object : DialogInterface.OnClickListener {
                
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    
                    dismiss() // pencereyi kapatır.
                    
                }
                
                
            }).create()
        
        
        return alert
        
        
        return super.onCreateDialog(savedInstanceState)
    }

}
