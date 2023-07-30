package com.example.myinterface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeatherDataByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Call<WeatherData>
}
