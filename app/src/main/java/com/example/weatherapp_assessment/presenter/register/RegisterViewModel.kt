package com.example.weatherapp_assessment.presenter.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCases: RegisterUseCases):
    ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun onEvent(events:RegisterEvents){
        when(events){
            is RegisterEvents.registerEvent -> {register(events.user)}
        }
    }

    private fun register(user: User) {
        viewModelScope.launch {
            try {
                val register=  registerUseCases.register(user)
                _registerResult.value=true
            }catch (e:Exception){
                _registerResult.value=false
            }
        }
    }
}