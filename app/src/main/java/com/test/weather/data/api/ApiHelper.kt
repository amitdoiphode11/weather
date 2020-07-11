package com.test.weather.data.api

import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.model.WeWeekWeather

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