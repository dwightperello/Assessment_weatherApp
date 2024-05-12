package com.example.weatherapp_assessment.presenter.weather.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.domain.usecase.local.weather.WeatherUseCases
import com.example.weatherapp_assessment.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor ( private val weatherUseCases: WeatherUseCases) : ViewModel() {

    private val _savedWeather:MutableLiveData<ResultState<List<weather>>> = MutableLiveData()
    val savedWeather:LiveData<ResultState<List<weather>>> get() = _savedWeather

    private val _saving=MutableLiveData<Boolean>()
    val saving:LiveData<Boolean> = _saving

    init {
        onEvent(localWeatherEvents.getAllWeatherFromDb)
    }

    fun onEvent(events: localWeatherEvents){
        when(events){
            is localWeatherEvents.getAllWeatherFromDb -> {getAllSaved()}
            is localWeatherEvents.insertWeatherToDB -> {saveToDb(events.weather)}
        }
    }

    private fun saveToDb(weather: weather) {
        viewModelScope.launch {
            try {
                weatherUseCases.insertWeather(weather)
                _saving.postValue(true)
            }catch (e:Exception){
                _saving.postValue(false)
            }

        }
    }

    private fun getAllSaved(){
        viewModelScope.launch {
            weatherUseCases.getAllWeather().onEach {
                _savedWeather.value=it
            }.launchIn(viewModelScope)
        }
    }

}