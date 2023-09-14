package com.example.mygpamap

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val locationListener: LocationListener

    private val _state = mutableStateOf(
        MapState(
            null, PolygonOptions().strokeWidth(5f).strokeColor(R.color.black)
        )
    )
    val state: State<MapState> = _state

    init {
        locationListener = LocationListener(application, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                val location = locationResult.lastLocation
                val polyLineOptions = state.value.polyLineOptions

                _state.value = state.value.copy(
                    location = location,
                    polyLineOptions = polyLineOptions.add(
                        LatLng(
                            location?.latitude ?: 0.0,
                            location?.longitude ?: 0.0
                        )
                    )
                )
            }
        })

    }
}