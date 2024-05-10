package com.example.weatherapp_assessment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp_assessment.MainCoroutineRule
import com.example.weatherapp_assessment.data.local.fakeLoginRepo
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCase
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCases
import com.example.weatherapp_assessment.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

import com.example.weatherapp_assessment.presenter.register.RegisterEvents
import com.example.weatherapp_assessment.presenter.register.RegisterViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private lateinit var viewmodel :RegisterViewModel
    val register= RegisterUseCase(fakeLoginRepo())
    val registerUseCase= RegisterUseCases(register)



    @Before
    fun setup() {
        viewmodel = RegisterViewModel(registerUseCase)
    }

    @Test
    fun `insert user`()= runBlocking{

        val result = User(
            Email = "test",
            Name = "test",
            Password = "pass1234",
            Address = "test address"
        )
        viewmodel.onEvent(events = RegisterEvents.registerEvent(result))
        val   value= viewmodel.registerResult.getOrAwaitValue()
        assertThat(value).isTrue()
    }


}