package com.example.weatherapp_assessment.presenter.main

import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.presenter.register.RegisterEvents

sealed class LoginEvents {

    data class login(val email:String,val password:String):LoginEvents()

}