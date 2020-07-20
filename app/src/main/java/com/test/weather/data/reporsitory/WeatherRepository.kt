package com.test.weather.data.reporsitory

import com.test.weather.data.api.ApiHelper
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ) = apiHelper.getCurrentWeather(city, employeeId, units)


    suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ) = apiHelper.getWeekWeather(city, employeeId, units)

}