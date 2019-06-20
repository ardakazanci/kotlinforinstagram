/*
 * *
 *  * Created by Arda Kazancı on 6/20/19 7:19 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/20/19 7:19 PM
 *
 */

package com.ardakazanci.instagramkotlin.model

class User{

    var userUID :String?= null
    var userEmail:String?=null
    var userPassword:String?=null
    var userUserName:String?=null
    var userPersonelName:String?=null

    constructor(){}



    constructor(userUID:String?,userEmail: String?, userPassword: String?, userUserName: String?, userPersonelName: String?) {
        this.userEmail = userEmail
        this.userPassword = userPassword
        this.userUserName = userUserName
        this.userPersonelName = userPersonelName
    }


}