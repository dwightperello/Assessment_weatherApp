package com.example.weatherapp_assessment.data.repository.local

import com.example.weatherapp_assessment.data.local.dao.LoginDao
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.repository.local.LoginRepository
import javax.inject.Inject

class loginRepositoryImpl @Inject constructor(private val loginDao: LoginDao): LoginRepository {

    override suspend fun insertUser(user: User) {
        return loginDao.insertUser(user)
    }

    override suspend fun Login(email: String, password: String): User {
        return loginDao.Login(email,password)
    }


}