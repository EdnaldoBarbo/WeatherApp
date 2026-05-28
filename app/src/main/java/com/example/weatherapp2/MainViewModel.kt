package com.example.weatherapp2

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.example.weatherapp2.model.City

private fun getCities() = List(20) { i ->
    City(name = "Cidade $i", weather = "Carregando clima...")
}

class MainViewModel : ViewModel() {
    private val _cities = getCities().toMutableStateList()
    val cities: List<City>
        get() = _cities

    fun remove(city: City) {
        _cities.remove(city)
    }

    fun add(name: String, location: LatLng? = null) {
        _cities.add(City(name = name, location = location))
    }
}
