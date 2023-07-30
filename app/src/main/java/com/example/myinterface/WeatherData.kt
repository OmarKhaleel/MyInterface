package com.example.myinterface

data class WeatherData(
    val main: MainData,
    val weather: List<Weather>
)

data class MainData(
    val temp: Double
)

data class Weather(
    val description: String
)
