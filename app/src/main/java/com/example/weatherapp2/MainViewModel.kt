package com.example.weatherapp2

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.example.weatherapp2.db.fb.FBCity
import com.example.weatherapp2.db.fb.FBDatabase
import com.example.weatherapp2.db.fb.FBUser
import com.example.weatherapp2.db.fb.toFBCity
import com.example.weatherapp2.model.City
import com.example.weatherapp2.model.User

class MainViewModel(private val db: FBDatabase) : ViewModel(), FBDatabase.Listener {
    private val _cities = mutableStateListOf<City>()
    val cities: List<City>
        get() = _cities

    private val _user = mutableStateOf<User?>(null)
    val user: User?
        get() = _user.value

    init {
        db.setListener(this)
    }

    fun remove(city: City) {
        db.remove(city.toFBCity())
    }

    fun add(name: String, location: LatLng? = null) {
        db.add(City(name = name, location = location).toFBCity())
    }

    override fun onUserLoaded(user: FBUser) {
        _user.value = user.toUser()
    }

    override fun onUserSignOut() {
        // TODO
    }

    override fun onCityAdded(city: FBCity) {
        _cities.add(city.toCity())
    }

    override fun onCityUpdated(city: FBCity) {
        // TODO
    }

    override fun onCityRemoved(city: FBCity) {
        _cities.remove(city.toCity())
    }
}

class MainViewModelFactory(private val db: FBDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
