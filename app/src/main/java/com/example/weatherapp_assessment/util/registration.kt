package com.example.weatherapp_assessment.util

object registration {

    private val existingUsers = listOf("test@test.com", "test1.test.com")

    fun validateRegistration(
        email:String,
        name:String,
        password:String,
        address:String
    ):Boolean{
        if(email.isEmpty() || password.isEmpty()){
            return false
        }
        if(email in existingUsers) {
            return false
        }
        return true
    }
}