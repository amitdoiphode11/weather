package com.test.weather.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeWeather(
    var main: String? = null,
    var description: String? = null
) : Parcelable
