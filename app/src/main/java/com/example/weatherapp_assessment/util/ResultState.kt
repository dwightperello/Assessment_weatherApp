package com.example.weatherapp_assessment.util


sealed class ResultState<out T>(val data:T? =null, val message:String?= null,val code:Int?= null) {
    class Success<T>(data: T?):ResultState<T>(data)
    class Error<T>(message: String?,data: T?= null,code: Int?= null):ResultState<T>(data,message,code)
    class Loading<T>(data: T?= null):ResultState<T>(data)
}
