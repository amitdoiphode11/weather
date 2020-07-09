package com.test.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.weather.data.WeCurrentWeather
import com.test.weather.data.WeWeekWeather

class SharedViewModel : ViewModel() {
    var weatherUnit: MutableLiveData<String>? = null
    var tabSelectedListener: MutableLiveData<String>? = null

    init {
        weatherUnit?.value = "metric"
        tabSelectedListener?.value = "list"
    }

    suspend fun getCityCurrentWeather(): LiveData<ArrayList<WeCurrentWeather>>? {
        return null
    }

    suspend fun getWeekWeather(city: String?): LiveData<WeWeekWeather>? {
        return null
    }

    fun setUnit(units: String?) {
        weatherUnit?.value = units
    }

    fun setTab(tab: String?) {
        tabSelectedListener?.value = tab
    }
}