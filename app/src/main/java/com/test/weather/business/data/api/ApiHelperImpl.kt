package com.test.weather.business.data.api

import com.test.weather.business.data.model.WeCurrentWeather
import com.test.weather.business.data.model.WeWeekWeather
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService?) : ApiHelper {
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