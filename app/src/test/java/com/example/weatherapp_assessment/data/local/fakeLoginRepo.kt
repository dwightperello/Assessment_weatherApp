package com.example.weatherapp_assessment.data.local

import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.repository.local.LoginRepository
import javax.security.auth.login.LoginException

class fakeLoginRepo : LoginRepository {

   val users = mutableListOf<User>()


    override suspend fun insertUser(user: User) {
      users.add(user)
    }

    override suspend fun Login(email: String, password: String): User {
        if (shouldReturnError) {
            throw LoginException("Login failed")
        }
        val user = users.find { it.Email == email && it.Password == password }
        return user ?: throw LoginException("User not found")
    }

    var shouldReturnError = false

    fun setshouldReturnError(value:Boolean){
        shouldReturnError=value
    }
}