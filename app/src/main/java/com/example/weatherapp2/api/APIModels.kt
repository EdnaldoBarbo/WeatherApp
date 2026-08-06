package com.example.weatherapp2.api

import com.example.weatherapp2.model.Forecast
import com.example.weatherapp2.model.Weather
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class APICondition(
    val text: String,
    val icon: String
)

data class APIWeather(
    val last_updated: String,
    val temp_c: Double,
    val condition: APICondition
)

data class APICurrentWeather(
    val current: APIWeather
) {
    fun toWeather(): Weather {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val localDateTime = LocalDateTime.parse(current.last_updated, inputFormatter)
        
        return Weather(
            date = localDateTime.format(outputFormatter),
            desc = current.condition.text,
            temp = current.temp_c,
            imgUrl = "https:${current.condition.icon}"
        )
    }
}

data class APIForecastDay(
    val date: String,
    val day: APIForecastDayDetail
) {
    fun toForecast(): Forecast {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM")
        val localDate = LocalDate.parse(date, inputFormatter)

        return Forecast(
            date = localDate.format(outputFormatter),
            weather = day.condition.text,
            tempMin = day.mintemp_c,
            tempMax = day.maxtemp_c,
            imgUrl = "https:${day.condition.icon}"
        )
    }
}

data class APIForecastDayDetail(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: APICondition
)

data class APIForecast(
    val forecastday: List<APIForecastDay>
)

data class APIWeatherForecast(
    val forecast: APIForecast
)
