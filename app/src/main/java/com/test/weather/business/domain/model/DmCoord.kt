package com.test.weather.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DmCoord(
    var lon: Double? = null,
    var lat: Double? = null
) : Parcelable