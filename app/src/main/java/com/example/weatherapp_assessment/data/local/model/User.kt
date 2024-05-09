package com.example.weatherapp_assessment.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val Id: Int=0,
    val Email:String,
    val Name:String,
    val Password:String,
    val Address:String
): Parcelable
