package com.test.weather.network

import com.test.weather.data.WeCurrentWeather
import com.test.weather.data.WeWeekWeather
import retrofit2.Response

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