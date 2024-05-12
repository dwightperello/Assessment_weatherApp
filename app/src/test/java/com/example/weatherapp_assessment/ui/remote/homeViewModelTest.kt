package com.example.weatherapp_assessment.ui.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp_assessment.MainCoroutineRule
import com.example.weatherapp_assessment.data.remote.model.*
import com.example.weatherapp_assessment.data.repository.TestRepository
import com.example.weatherapp_assessment.domain.usecase.remote.WeatherUseCase
import com.example.weatherapp_assessment.domain.usecase.remote.getWeatherUsecase
import com.example.weatherapp_assessment.getOrAwaitValue
import com.example.weatherapp_assessment.presenter.weather.dashboard.DashboardViewModel
import com.example.weatherapp_assessment.presenter.weather.home.HomeEvent
import com.example.weatherapp_assessment.presenter.weather.home.HomeViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class homeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    val getremote = getWeatherUsecase(TestRepository())
    val weatherusecase=WeatherUseCase(getremote)

    @Before
    fun setup(){
        viewModel= HomeViewModel(weatherusecase)
    }

    @Test
    fun `get weather from remote`() = runBlocking{
        val weatherData = WeatherResponse(
            coord = Coord(121.0197, 14.4793),
            weather = listOf(Weather(801, "Clouds", "few clouds", "02d")),
            base = "stations",
            main = Main(307.66, 314.66, 305.85, 309.08, 1009, 57),
            visibility = 10000,
            wind = Wind(5.81, 120, 6.26),
            clouds = Clouds(20),
            dt = 1715233974,
            sys = Sys(2, 2005706, "PH", 1715203813, 1715249672),
            timezone = 28800,
            id = 1694781,
            name = "Paranaque City",
            cod = 200
        )
        val testRepository = TestRepository()
        testRepository.addweather(weatherData)

        viewModel.onEvent(events = HomeEvent.getWeather(121.0197,121.0197,"ABC"))
        val value = viewModel.weather.getOrAwaitValue()


      //  verify(testRepository).getWeather(121.0197,121.0197,"ABC")
        Truth.assertThat(value).isNotNull()
    }

}