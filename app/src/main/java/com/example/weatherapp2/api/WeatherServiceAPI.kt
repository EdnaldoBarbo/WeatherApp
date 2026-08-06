package com.example.weatherapp2.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.weatherapp2.BuildConfig

interface WeatherServiceAPI {
    @GET("search.json?key=$API_KEY&lang=pt_br")
    fun search(@Query("q") query: String): Call<List<APILocation>?>

    @GET("current.json?key=$API_KEY&lang=pt")
    fun weather(@Query("q") query: String): Call<APICurrentWeather?>

    @GET("forecast.json?key=$API_KEY&days=10&lang=pt")
    fun forecast(@Query("q") query: String): Call<APIWeatherForecast?>

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        // Para usar na anotação, a chave precisa ser uma constante literal neste contexto
        const val API_KEY = "ed3d70c5b146498f8c9191936260608"
    }
}
