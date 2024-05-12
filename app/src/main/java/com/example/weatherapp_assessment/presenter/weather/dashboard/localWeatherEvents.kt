package com.example.weatherapp_assessment.presenter.weather.dashboard

import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.presenter.register.RegisterEvents

sealed class localWeatherEvents {

    data class insertWeatherToDB(val weather: weather):localWeatherEvents()
    object getAllWeatherFromDb: localWeatherEvents()
}