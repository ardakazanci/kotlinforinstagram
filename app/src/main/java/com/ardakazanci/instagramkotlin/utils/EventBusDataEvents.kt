/*
 * *
 *  * Created by Arda Kazancı on 6/19/19 8:52 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/19/19 8:52 PM
 *
 */

package com.ardakazanci.instagramkotlin.utils

import com.ardakazanci.instagramkotlin.model.User

class EventBusDataEvents {
    
    internal class TelefonNoGonder(var telNo: String)
    internal class EmailGonder(var email: String)
    
    
    // Kullanıcını bilgilerinin ayarlar ekranına gönderilmesini için gerekli.
    internal class KullaniciBilgileriniGonder(var user: User?)
    
    
}