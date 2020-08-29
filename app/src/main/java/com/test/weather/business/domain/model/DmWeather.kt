package com.test.weather.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DmWeather(
    var main: String? = null,
    var description: String? = null
) : Parcelable
