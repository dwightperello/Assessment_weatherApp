package com.example.weatherapp_assessment.presenter.weather.home

import com.example.weatherapp_assessment.presenter.main.LoginEvents

sealed class HomeEvent {
    data class getWeather(val lat:Double,val lon:Double,val key:String ): HomeEvent()
}