package com.example.weatherapp2.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.example.weatherapp2.MainViewModel
import com.example.weatherapp2.R

@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val camPosState = rememberCameraPositionState()

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = camPosState,
        properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true),
        onMapClick = {
            viewModel.addCity(it)
        }
    ) {
        viewModel.cities.forEach { city ->
            if (city.location != null) {
                val weather = viewModel.weather(city.name)
                val image = weather.bitmap ?: ContextCompat.getDrawable(context, R.drawable.loading)!!.toBitmap()
                val marker = BitmapDescriptorFactory.fromBitmap(image.scale(120, 120))
                
                Marker(
                    state = MarkerState(position = city.location),
                    title = city.name,
                    snippet = weather.desc,
                    icon = marker
                )
            }
        }
    }
}
