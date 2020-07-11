package com.test.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeWeekWeather(val list: ArrayList<WeCurrentWeather>?) : Parcelable