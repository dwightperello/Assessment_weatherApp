package com.example.weatherapp_assessment.data.repository.local

import com.example.weatherapp_assessment.data.local.dao.weatherDao
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.domain.repository.local.weatherRepositoryLocal
import javax.inject.Inject

class weatherLocalImpl @Inject constructor(private val weatherDao: weatherDao):
    weatherRepositoryLocal {
    override suspend fun insertWeather(weather: weather) {
        return weatherDao.InsertWeather(weather)
    }

    override suspend fun getAllWethers(): List<weather> {
        return  weatherDao.getAllWeather()
    }


}