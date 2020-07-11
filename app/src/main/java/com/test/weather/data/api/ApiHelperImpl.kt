package com.test.weather.data.api

import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.model.WeWeekWeather

class ApiHelperImpl(private val apiService: ApiService?) : ApiHelper {
    override suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): WeCurrentWeather? {
        return apiService?.getCurrentWeather(city, employeeId, units)
    }

    override suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): WeWeekWeather? {
        return apiService?.getWeekWeather(city, employeeId, units)
    }
}