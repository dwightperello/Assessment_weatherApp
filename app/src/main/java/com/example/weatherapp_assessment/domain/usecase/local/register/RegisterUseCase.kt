package com.example.weatherapp_assessment.domain.usecase.local.register

import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.repository.local.LoginRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(user: User){
        return loginRepository.insertUser(user)
    }
}