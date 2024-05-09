package com.example.weatherapp_assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp_assessment.data.local.model.weather


@Dao
interface weatherDao {

    @Insert
    suspend fun InsertWeather(weather: weather)

    @Query("SELECT * FROM weather")
    suspend fun getAllWeather():List<weather>
}