package com.test.weather.business.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeWeekWeather(val list: ArrayList<WeCurrentWeather>?) : Parcelable