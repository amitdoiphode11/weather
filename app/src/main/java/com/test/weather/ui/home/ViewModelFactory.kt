package com.test.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.weather.data.api.ApiHelper
import com.test.weather.data.reporsitory.WeatherRepository
import com.test.weather.ui.home.list.WeatherListViewModel
import com.test.weather.ui.home.map.WeatherMapViewModel

class ViewModelFactory(private val weatherRepository: WeatherRepository?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherListViewModel::class.java)) {
            return WeatherListViewModel(weatherRepository) as T
        }
        if (modelClass.isAssignableFrom(WeatherMapViewModel::class.java)) {
            return WeatherMapViewModel(weatherRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}