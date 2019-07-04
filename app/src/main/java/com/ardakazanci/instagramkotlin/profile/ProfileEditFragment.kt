/*
 * *
 *  * Created by Arda Kazancı on 6/16/19 8:01 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 6/16/19 8:01 PM
 *
 */

package com.ardakazanci.instagramkotlin.profile


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.instagramkotlin.R
import com.ardakazanci.instagramkotlin.model.User
import com.ardakazanci.instagramkotlin.utils.EventBusDataEvents
import com.ardakazanci.instagramkotlin.utils.UniversalImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ProfileEditFragment : Fragment() {

    lateinit var circleProfilePictureImageView: CircleImageView
    lateinit var getUserInfo: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    
    
        val v = inflater.inflate(R.layout.fragment_profile_edit, container, false)
    
    
        setupUserInfoEventListener(v)
      


        v.imageview_profileedit_close.setOnClickListener {

            activity?.onBackPressed()

        }


        return v
    }
    
    /**
     * EventBus ile dinlediğimiz dataları burada işleyeceğiz.
     * v: View elemanı aracılığıyla iç view elemanlarına erişmek istiyoruz.
     */
    private fun setupUserInfoEventListener(v: View?) {
        
        
        
        v!!.edittext_profilesettings_name.setText(getUserInfo.userPersonelName)
        v.edittext_profilesettings_username.setText(getUserInfo.userUserName)
        
        
        //v.circleimageview_profilesettings_profilepicture.
        
        val userProfilePicture = getUserInfo.userDetails?.userProfilePicture
        
        UniversalImageLoader.setImage(
            userProfilePicture,
            v.circleimageview_profilesettings_profilepicture,
            v.progressbar_profilesettings_pictureloading,
            ""
        )
        
        
        
        // isNullOrEmpty kontrolü sağlanabilir fakat ? işareti ile aynı süreci işletmiş olduk. Null olabilir gibi...
        v.edittext_profilesettings_biography.setText(getUserInfo.userDetails?.userBiography)
        v.edittext_profilesettings_website.setText(getUserInfo.userDetails?.userWebsite)
        
        
    }
    
    /**
     * ///////////// EVENTBUS İŞLEMLERİ   /////////////
     */
    @Subscribe(sticky = true)
    internal fun onUserInfoEvent(userInfos: EventBusDataEvents.KullaniciBilgileriniGonder) {
        
        getUserInfo = userInfos.user!!
    
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
