package com.example.weatherapp_assessment.domain.repository.local

import com.example.weatherapp_assessment.data.local.model.weather

interface weatherRepositoryLocal {

    suspend fun insertWeather(weather: weather)
    suspend fun getAllWethers():List<weather>
}