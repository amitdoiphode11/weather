package com.test.weather.framework.datasource.network

import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import com.test.weather.framework.datasource.network.retrofit.ApiService
import javax.inject.Inject

class ApiHelperImpl constructor(private val apiService: ApiService?) : ApiHelper {
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