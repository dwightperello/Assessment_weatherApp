package com.example.weatherapp_assessment.domain.usecase

import com.example.weatherapp_assessment.data.remote.model.*
import com.example.weatherapp_assessment.data.repository.TestRepository
import com.example.weatherapp_assessment.domain.usecase.remote.getWeatherUsecase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class getWeather {

    private lateinit var getWeatherUseCase: getWeatherUsecase
    private lateinit var testRepository: TestRepository

    @Before
    fun setUp(){
        testRepository= TestRepository()
        getWeatherUseCase= getWeatherUsecase(testRepository)
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
        runBlocking {
            testRepository.addweather(weatherData)
        }
    }

    @Test
    fun `get weather`()= runBlocking {
        val res= getWeatherUseCase(121.0197,14.4793,"ABCD")
        assertThat(res).isNotNull()
    }
}