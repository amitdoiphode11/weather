package com.test.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.weather.data.reporsitory.WeatherRepositoryImpl
import com.test.weather.ui.home.list.WeatherListViewModel
import com.test.weather.ui.home.map.WeatherMapViewModel

class ViewModelFactory(private val weatherRepositoryImpl: WeatherRepositoryImpl?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherListViewModel::class.java)) {
            return WeatherListViewModel(weatherRepositoryImpl) as T
        }
        if (modelClass.isAssignableFrom(WeatherMapViewModel::class.java)) {
            return WeatherMapViewModel(weatherRepositoryImpl) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}