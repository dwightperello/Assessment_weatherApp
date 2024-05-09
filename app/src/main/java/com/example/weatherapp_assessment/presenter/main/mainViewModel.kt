package com.example.weatherapp_assessment.presenter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.usecase.local.login.UserUserCases
import com.example.weatherapp_assessment.presenter.register.RegisterEvents
import com.example.weatherapp_assessment.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(private val users:UserUserCases):ViewModel() {

    private val _user: MutableLiveData<ResultState<User>> = MutableLiveData()
    val user:LiveData<ResultState<User>> get() = _user

    fun onEvent(events: LoginEvents){
        when(events){
            is LoginEvents.login -> {Login(events.email,events.password)}
        }
    }

    private fun Login(email: String, password: String) {
        viewModelScope.launch {
          users.login(email,password).onEach {
              _user.value=it
          }.launchIn(viewModelScope)
        }
    }


}