package com.example.weatherapp_assessment.data.repository

import com.example.weatherapp_assessment.data.remote.model.WeatherResponse
import com.example.weatherapp_assessment.domain.repository.remote.weatherRepository

class TestRepository : weatherRepository {

    private val weather= mutableListOf<WeatherResponse>()

    override suspend fun getWeather(lat: Double, lon: Double, id: String): WeatherResponse {
        return weather.firstOrNull() ?: throw NoSuchElementException("No weather data available")
    }

    fun addweather(weatherResponse: WeatherResponse){
        weather.add(weatherResponse)
    }
}