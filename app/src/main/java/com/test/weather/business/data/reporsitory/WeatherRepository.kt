package com.test.weather.business.data.reporsitory

import com.test.weather.business.data.model.WeCurrentWeather
import com.test.weather.business.data.model.WeWeekWeather
import com.test.weather.business.utils.state.DataState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ): Flow<DataState<WeCurrentWeather?>?>


    suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String? = "metric"
    ): Flow<DataState<WeWeekWeather?>?>

}