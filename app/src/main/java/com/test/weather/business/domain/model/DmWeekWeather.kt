package com.test.weather.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DmWeekWeather(val list: ArrayList<DmCurrentWeather>?) : Parcelable