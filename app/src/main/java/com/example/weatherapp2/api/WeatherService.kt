package com.example.weatherapp2.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService(private val context: Context) {
    private var weatherAPI: WeatherServiceAPI
    private val imageLoader = ImageLoader.Builder(context).allowHardware(false).build()

    init {
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(WeatherServiceAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherAPI = retrofitAPI.create(WeatherServiceAPI::class.java)
    }

    private fun <T> enqueue(call: Call<T?>, onResponse: ((T?) -> Unit)? = null) {
        call.enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                onResponse?.invoke(response.body())
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse?.invoke(null)
            }
        })
    }

    fun getName(lat: Double, lng: Double, onResponse: (String?) -> Unit) {
        search("$lat, $lng") { loc -> onResponse(loc?.name) }
    }

    fun getLocation(name: String, onResponse: (lat: Double?, long: Double?) -> Unit) {
        search(name) { loc -> onResponse(loc?.lat, loc?.lon) }
    }

    private fun search(query: String, onResponse: (APILocation?) -> Unit) {
        val call = weatherAPI.search(query)
        enqueue(call) { list ->
            onResponse(list?.let { if (it.isNotEmpty()) it[0] else null })
        }
    }

    fun getWeather(name: String, onResponse: (APICurrentWeather?) -> Unit) {
        enqueue(weatherAPI.weather(name), onResponse)
    }

    fun getForecast(name: String, onResponse: (APIWeatherForecast?) -> Unit) {
        enqueue(weatherAPI.forecast(name), onResponse)
    }

    fun getBitmap(imgUrl: String, onResponse: (Bitmap?) -> Unit) {
        val request = ImageRequest.Builder(context)
            .data(imgUrl)
            .allowHardware(false)
            .target(
                onSuccess = { drawable -> onResponse((drawable as BitmapDrawable).bitmap) },
                onError = { onResponse(null) }
            ).build()
        imageLoader.enqueue(request)
    }
}
