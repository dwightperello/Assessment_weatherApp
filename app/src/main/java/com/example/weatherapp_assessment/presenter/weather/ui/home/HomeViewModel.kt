package com.example.weatherapp_assessment.presenter.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.data.remote.model.Weather
import com.example.weatherapp_assessment.data.remote.model.WeatherResponse
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCases
import com.example.weatherapp_assessment.domain.usecase.local.weather.WeatherUseCases
import com.example.weatherapp_assessment.domain.usecase.remote.WeatherUseCase
import com.example.weatherapp_assessment.presenter.main.LoginEvents
import com.example.weatherapp_assessment.util.ResultState
import com.example.weatherapp_assessment.util.TempManager
import com.example.weatherapp_assessment.util.constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase,private val weatherUseCases: WeatherUseCases) : ViewModel() {

   private val _weather:MutableLiveData<ResultState<WeatherResponse>> = MutableLiveData()
    val weather:LiveData<ResultState<WeatherResponse>> get() = _weather

init {
    onEvent(HomeEvent.getWeather(TempManager.latitude!!,TempManager.long!!,constants.API_KEY))
}

    fun onEvent(events: HomeEvent){
        when(events){
            is HomeEvent.getWeather -> {getWeatherFromAPI(events.lat,events.lon,events.key)}
        }
    }

    private fun getWeatherFromAPI(lat: Double, lon: Double, key: String) {
        viewModelScope.launch {
            weatherUseCase.getweatherusecase(lat,lon,key).onEach {
                _weather.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun insertWeather(weather: weather){
        viewModelScope.launch {
            weatherUseCases.insertWeather(weather)
        }
    }
}