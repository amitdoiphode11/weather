package com.test.weather.business.data.network

import com.test.weather.framework.datasource.network.ApiHelper
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import javax.inject.Inject

class WeatherDataSourceImpl constructor(private val apiHelper: ApiHelper) :
    WeatherDataSource {
    override suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): WeCurrentWeather? {
        return apiHelper.getCurrentWeather(city, employeeId, units)
    }

    override suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): WeWeekWeather? {
        return apiHelper.getWeekWeather(city, employeeId, units)
    }

}