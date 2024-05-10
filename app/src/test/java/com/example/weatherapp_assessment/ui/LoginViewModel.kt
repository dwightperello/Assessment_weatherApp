package com.example.weatherapp_assessment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp_assessment.MainCoroutineRule
import com.example.weatherapp_assessment.data.local.fakeLoginRepo
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.domain.usecase.local.login.LoginUseCase
import com.example.weatherapp_assessment.domain.usecase.local.login.UserUserCases
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCase
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCases
import com.example.weatherapp_assessment.getOrAwaitValue
import com.example.weatherapp_assessment.presenter.main.LoginEvents
import com.example.weatherapp_assessment.presenter.main.mainViewModel
import com.example.weatherapp_assessment.presenter.register.RegisterEvents
import com.example.weatherapp_assessment.presenter.register.RegisterViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class LoginViewModel {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()
    private lateinit var viewmodel : mainViewModel
    val user= LoginUseCase(fakeLoginRepo())
    val userCases= UserUserCases(user)

    @Before
    fun setup() {
        viewmodel = mainViewModel(userCases)
    }

    @Test
    fun `login user`() = runBlocking{

        val mockRepository = fakeLoginRepo()
        mockRepository.setshouldReturnError(false)

        val user = User(
            Email = "test",
            Name = "test",
            Password = "pass1234",
            Address = "test address"
        )


        mockRepository.insertUser(user)
        viewmodel.onEvent( events = LoginEvents.login(user.Email,user.Password))


        val value = viewmodel.user.getOrAwaitValue()
        Truth.assertThat(value).isNotNull()
    }
}