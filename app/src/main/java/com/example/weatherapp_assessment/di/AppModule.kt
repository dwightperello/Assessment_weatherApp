package com.example.weatherapp_assessment.di

import android.app.Application
import com.example.weatherapp_assessment.data.local.AppDatabase
import com.example.weatherapp_assessment.data.local.dao.LoginDao
import com.example.weatherapp_assessment.data.local.dao.weatherDao
import com.example.weatherapp_assessment.data.remote.weatherAPI
import com.example.weatherapp_assessment.data.repository.local.loginRepositoryImpl
import com.example.weatherapp_assessment.data.repository.local.weatherLocalImpl
import com.example.weatherapp_assessment.data.repository.remote.weatherRepositoryImpl
import com.example.weatherapp_assessment.domain.repository.local.LoginRepository
import com.example.weatherapp_assessment.domain.repository.local.weatherRepositoryLocal
import com.example.weatherapp_assessment.domain.repository.remote.weatherRepository
import com.example.weatherapp_assessment.domain.usecase.local.login.LoginUseCase
import com.example.weatherapp_assessment.domain.usecase.local.login.UserUserCases
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCase
import com.example.weatherapp_assessment.domain.usecase.local.register.RegisterUseCases
import com.example.weatherapp_assessment.domain.usecase.local.weather.WeatherUseCases
import com.example.weatherapp_assessment.domain.usecase.local.weather.getAllWeatherUseCase
import com.example.weatherapp_assessment.domain.usecase.local.weather.insertWeatherUseCase
import com.example.weatherapp_assessment.domain.usecase.remote.WeatherUseCase
import com.example.weatherapp_assessment.domain.usecase.remote.getWeatherUsecase
import com.example.weatherapp_assessment.util.constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDB(context)
    }


    @Provides
    @Singleton
    fun provideloginRepo(loginDao: LoginDao): LoginRepository = loginRepositoryImpl(loginDao)

    @Provides
    @Singleton
    fun providelocalweatherRepo(weatherDao: weatherDao): weatherRepositoryLocal = weatherLocalImpl(weatherDao)

    @Provides
    @Singleton
    fun provideLoginDao(appDatabase: AppDatabase):LoginDao = appDatabase.provideLoginDao()


    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase):weatherDao = appDatabase.provideWeatherDao()

    @Provides
    @Singleton
    fun providUserUseCase(loginRepository: LoginRepository): UserUserCases {
        return UserUserCases(
            login = LoginUseCase(loginRepository)
        )
    }

    @Provides
    @Singleton
    fun providRegisterUseCase(loginRepository: LoginRepository): RegisterUseCases {
        return RegisterUseCases(
            register = RegisterUseCase(loginRepository)
            )
    }

    @Provides
    @Singleton
    fun providWeatherUseCase(weatherRepositoryLocal: weatherRepositoryLocal): WeatherUseCases {
        return WeatherUseCases(
            insertWeather = insertWeatherUseCase(weatherRepositoryLocal),
            getAllWeather = getAllWeatherUseCase(weatherRepositoryLocal)
        )
    }

    @Provides
    @Singleton
    fun provideAPI(): weatherAPI {
        return Retrofit.Builder()
            .baseUrl(constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(weatherAPI::class.java)
    }



    @Provides
    @Singleton
    fun provideAPIrepository(weatherAPI: weatherAPI): weatherRepository = weatherRepositoryImpl(weatherAPI)

    @Provides
    @Singleton
    fun provideUseCases(weatherRepository: weatherRepository): WeatherUseCase {
        return WeatherUseCase(
            getweatherusecase = getWeatherUsecase(weatherRepository)
        )
    }

}