package com.test.weather.network

import com.test.weather.data.WeCurrentWeather
import com.test.weather.data.WeWeekWeather
import retrofit2.Response

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
    ): Response<WeWeekWeather?>?

}