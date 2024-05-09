package com.example.weatherapp_assessment.data.repository.remote

import com.example.weatherapp_assessment.data.remote.model.WeatherResponse
import com.example.weatherapp_assessment.data.remote.weatherAPI
import com.example.weatherapp_assessment.domain.repository.remote.weatherRepository


class weatherRepositoryImpl(private val weatherAPI: weatherAPI): weatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, id: String): WeatherResponse {
        return weatherAPI.getWeather(lat,lon,id)
    }
}