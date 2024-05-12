package com.example.weatherapp_assessment.data.local

import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.domain.repository.local.weatherRepositoryLocal

class fakeWeatherLocRepository : weatherRepositoryLocal {

    val wethearToAdd = mutableListOf<weather>()

    override suspend fun insertWeather(weather: weather) {
      wethearToAdd.add(weather)
    }

    override suspend fun getAllWethers(): List<weather> {
       return  wethearToAdd.toList()
    }

    var shouldReturnError = false

    fun setshouldReturnError(value:Boolean){
        shouldReturnError=value
    }
}