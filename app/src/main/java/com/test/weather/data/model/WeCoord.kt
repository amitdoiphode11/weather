package com.test.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeCoord(
    var lon: Double? = null,
    var lat: Double? = null
) : Parcelable