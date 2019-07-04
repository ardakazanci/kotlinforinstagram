/*
 * *
 *  * Created by Arda Kazancı on 6/24/19 12:59 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/24/19 12:59 PM
 *
 */

package com.ardakazanci.instagramkotlin.model


/**
 * User Bilgileri Model Class ' ı
 */

class UserDetails {
    
    var userFollowerCount: String? = null // Kaç takipçim var
    var userFollowingCount: String? = null // Kaç kişiyi takip ediyorum.
    var userPostCount: String? = null // Kaç tane gönderim var.
    var userProfilePicture: String? = null
    var userBiography: String? = null
    var userWebsite: String? = null
    
    
    constructor()
    
    constructor(
        userFollowerCount: String?,
        userFollowingCount: String?,
        userPostCount: String?,
        userProfilePicture: String?,
        userBiography: String?,
        userWebsite: String?
    ) {
        this.userFollowerCount = userFollowerCount
        this.userFollowingCount = userFollowingCount
        this.userPostCount = userPostCount
        this.userProfilePicture = userProfilePicture
        this.userBiography = userBiography
        this.userWebsite = userWebsite
    }
    
    override fun toString(): String {
        return "UserDetails(userFollowerCount=$userFollowerCount, userFollowingCount=$userFollowingCount, userPostCount=$userPostCount, userProfilePicture=$userProfilePicture, userBiography=$userBiography, userWebsite=$userWebsite)"
    }
    
    
}