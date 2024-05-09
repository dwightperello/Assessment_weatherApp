package com.example.weatherapp_assessment.domain.repository.local

import com.example.weatherapp_assessment.data.local.model.User

interface LoginRepository {
    suspend fun insertUser(user: User)
    suspend fun Login(email:String,password:String):User
}