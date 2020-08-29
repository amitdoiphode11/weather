package com.test.weather.framework.datasource.network

import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather

interface ApiHelper {

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