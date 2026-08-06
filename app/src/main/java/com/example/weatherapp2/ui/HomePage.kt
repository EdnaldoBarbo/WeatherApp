package com.example.weatherapp2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp2.MainViewModel
import com.example.weatherapp2.R
import com.example.weatherapp2.model.Forecast

@Composable
fun ForecastItem(forecast: Forecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = forecast.date, fontSize = 12.sp)
        AsyncImage(
            model = forecast.imgUrl,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            error = painterResource(id = R.drawable.loading)
        )
        Text(text = "${forecast.tempMin.toInt()}° / ${forecast.tempMax.toInt()}°", fontSize = 14.sp)
    }
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val cityName = viewModel.city
    if (cityName == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Selecione uma cidade na lista ou mapa.")
        }
        return
    }

    val weather = viewModel.weather(cityName)
    val forecast = viewModel.forecast(cityName)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = cityName, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = weather.imgUrl,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            error = painterResource(id = R.drawable.loading)
        )
        Text(text = weather.desc, fontSize = 20.sp)
        Text(text = "${weather.temp.toInt()}°C", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(text = "Previsão para os próximos dias:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyRow {
            if (forecast != null) {
                items(forecast) { item ->
                    ForecastItem(item)
                }
            }
        }
    }
}
