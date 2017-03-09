package io.lassondehacks.aroundu_android.services

import android.location.Location

class LocationService {
    companion object {
        var lastLocation: Location? = null
            get
            set
    }
}