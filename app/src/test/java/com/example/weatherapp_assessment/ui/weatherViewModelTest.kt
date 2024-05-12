package com.example.weatherapp_assessment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp_assessment.MainCoroutineRule
import com.example.weatherapp_assessment.data.local.fakeWeatherLocRepository
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.domain.usecase.local.weather.WeatherUseCases
import com.example.weatherapp_assessment.domain.usecase.local.weather.getAllWeatherUseCase
import com.example.weatherapp_assessment.domain.usecase.local.weather.insertWeatherUseCase
import com.example.weatherapp_assessment.getOrAwaitValue
import com.example.weatherapp_assessment.presenter.weather.dashboard.DashboardViewModel
import com.example.weatherapp_assessment.presenter.weather.dashboard.localWeatherEvents
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class weatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DashboardViewModel

    val insert = insertWeatherUseCase(fakeWeatherLocRepository())
    val getweather= getAllWeatherUseCase(fakeWeatherLocRepository())
    val weatherusecase= WeatherUseCases(insert,getweather)
    @Before
    fun setup(){
        viewModel= DashboardViewModel(weatherusecase)
    }

    @Test
    fun `insert weather from API`(){
        val itemToinsert= weather(
            temp = "30.30",
            datestemp = "rain",
            description = "somewhere",
            sunrise = "10:20 AM ",
            sunset = "9:00 AM"
        )
        viewModel.onEvent(events = localWeatherEvents.insertWeatherToDB(itemToinsert))
        val   value= viewModel.saving.getOrAwaitValue()

        Truth.assertThat(value).isTrue()
    }

    @Test
    fun `get all weathers from DB`(){
        val itemToinsert= weather(
            temp = "30.30",
            datestemp = "rain",
            description = "somewhere",
            sunrise = "10:20 AM ",
            sunset = "9:00 AM"
        )
        viewModel.onEvent(events = localWeatherEvents.insertWeatherToDB(itemToinsert))
        val   value= viewModel.savedWeather.getOrAwaitValue()
        Truth.assertThat(value).isNotNull()
    }
}