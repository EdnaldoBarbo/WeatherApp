package com.example.weatherapp2.model

import android.graphics.Bitmap

data class Weather(
    val date: String,
    val desc: String,
    val temp: Double,
    val imgUrl: String,
    var bitmap: Bitmap? = null
) {
    companion object {
        val LOADING = Weather(
            date = "---",
            desc = "carregando...",
            temp = 0.0,
            imgUrl = ""
        )
    }
}
