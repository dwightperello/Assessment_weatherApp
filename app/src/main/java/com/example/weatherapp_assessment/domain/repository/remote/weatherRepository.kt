package com.example.weatherapp_assessment.domain.repository.remote

import com.example.weatherapp_assessment.data.remote.model.WeatherResponse

interface weatherRepository {
    suspend fun getWeather(
        lat:Double,
        lon :Double,
        id:String
    ): WeatherResponse
}