package com.test.weather.business.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeCurrentWeather(
    var name: String? = null,
    var coord: WeCoord? = null,
    var weather: ArrayList<WeWeather>? = null,
    var main: WeMain? = null,
    var dt: Long? = null,
    var isLoader: Boolean? = null,
    var isRetry: Boolean? = null
) : Parcelable
