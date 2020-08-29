package com.test.weather.framework.datasource.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeWeekWeather(val list: ArrayList<WeCurrentWeather>?) : Parcelable