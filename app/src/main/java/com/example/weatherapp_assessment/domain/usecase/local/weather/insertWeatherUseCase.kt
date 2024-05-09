package com.example.weatherapp_assessment.domain.usecase.local.weather

import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.domain.repository.local.weatherRepositoryLocal
import javax.inject.Inject


class insertWeatherUseCase @Inject constructor(private val weatherRepositoryLocal: weatherRepositoryLocal) {

    suspend operator fun invoke(weather: weather){
        return weatherRepositoryLocal.insertWeather(weather)
    }
}