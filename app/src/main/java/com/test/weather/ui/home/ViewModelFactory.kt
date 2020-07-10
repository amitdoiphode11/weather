package com.test.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.weather.network.ApiHelper
import com.test.weather.ui.home.list.WeatherListViewModel
import com.test.weather.ui.home.map.WeatherMapViewModel

class ViewModelFactory(private val apiHelper: ApiHelper?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherListViewModel::class.java)) {
            return WeatherListViewModel(apiHelper) as T
        }
        if (modelClass.isAssignableFrom(WeatherMapViewModel::class.java)) {
            return WeatherMapViewModel(apiHelper) as T
        }
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}