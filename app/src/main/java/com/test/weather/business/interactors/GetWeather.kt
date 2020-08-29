package com.test.weather.business.interactors

import com.test.weather.business.data.network.WeatherDataSource
import com.test.weather.business.domain.utils.state.DataState
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetWeather(
    private val weatherDataSource: WeatherDataSource
) {

    suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        unit: String? = "metric"
    ): Flow<DataState<WeCurrentWeather?>> = flow {
        emit(DataState.Loading)
        val networkResult =
            weatherDataSource.getCurrentWeather(
                city = city,
                employeeId = employeeId,
                units = unit
            )
        emit(DataState.Success(networkResult))
    }


    suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        unit: String? = "metric"
    ): Flow<DataState<WeWeekWeather?>> = flow {
        emit(DataState.Loading)
        val networkResult =
            weatherDataSource.getWeekWeather(
                city = city,
                employeeId = employeeId,
                units = unit
            )
        emit(DataState.Success(networkResult))
    }

}