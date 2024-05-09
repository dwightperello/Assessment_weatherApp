package com.example.weatherapp_assessment.data.remote

import com.example.weatherapp_assessment.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherAPI {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon :Double,
        @Query("appid")id:String
    ): WeatherResponse
}