package com.example.weatherapp_assessment.domain.usecase.local.login

import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.repository.local.LoginRepository
import com.example.weatherapp_assessment.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(email:String,password:String): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading())
        try {
            val user= loginRepository.Login(email,password)
            emit(ResultState.Success<User>(user))
        }catch (e:Exception){
            emit(ResultState.Error<User>(e.localizedMessage ?: "error on login"))
        }
    }
}