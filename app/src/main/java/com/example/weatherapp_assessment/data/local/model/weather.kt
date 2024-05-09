package com.example.weatherapp_assessment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class weather(
    @PrimaryKey(autoGenerate = true) val Id: Int=0,
    val temp:Double,
    val datestemp:String,
    val description:String,
    val sunrise:Long,
    val sunset:Long
)
