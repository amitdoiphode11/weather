package com.test.weather.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DmCurrentWeather(
    var name: String? = null,
    var coord: DmCoord? = null,
    var weather: ArrayList<DmWeather>? = null,
    var main: DmMain? = null,
    var dt: Long? = null,
    var isLoader: Boolean? = null,
    var isRetry: Boolean? = null
) : Parcelable
