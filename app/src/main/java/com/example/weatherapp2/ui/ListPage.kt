package com.example.weatherapp2.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp2.MainViewModel
import com.example.weatherapp2.R
import com.example.weatherapp2.model.City
import com.example.weatherapp2.ui.nav.Route

@Composable
fun CityItem(
    city: City,
    viewModel: MainViewModel,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weather = viewModel.weather(city.name)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = weather.imgUrl,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            error = painterResource(id = R.drawable.loading)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = city.name, fontSize = 24.sp)
            Text(text = weather.desc, fontSize = 16.sp)
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val cityList = viewModel.cities

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList, key = { it.name }) { city ->
            CityItem(
                city = city,
                viewModel = viewModel,
                onClose = { viewModel.remove(city) },
                onClick = {
                    viewModel.city = city.name
                    viewModel.page = Route.Home
                }
            )
        }
    }
}
