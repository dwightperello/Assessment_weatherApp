package com.example.weatherapp_assessment.presenter.register

import com.example.weatherapp_assessment.data.local.model.User


sealed class RegisterEvents {
    data class registerEvent(val user: User): RegisterEvents()
}