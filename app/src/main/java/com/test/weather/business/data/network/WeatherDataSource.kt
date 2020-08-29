package com.test.weather.business.data.network

import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather

interface WeatherDataSource {
    suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ): WeCurrentWeather?


    suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ): WeWeekWeather?

}