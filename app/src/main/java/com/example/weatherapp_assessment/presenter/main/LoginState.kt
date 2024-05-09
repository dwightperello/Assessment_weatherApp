package com.example.weatherapp_assessment.presenter.main

import com.example.weatherapp_assessment.data.local.model.User


data class LoginState(
    val isLoading:Boolean=false,
    val user: User?= null,
    val error:String?= null
)