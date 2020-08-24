package com.test.weather.data.reporsitory

import com.test.weather.data.api.ApiHelper
import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.model.WeWeekWeather
import com.test.weather.utils.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) :
    WeatherRepository {
    override suspend fun getCurrentWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): Flow<DataState<WeCurrentWeather?>?> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(apiHelper.getCurrentWeather(city, employeeId, units)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getWeekWeather(
        city: String?,
        employeeId: String?,
        units: String?
    ): Flow<DataState<WeWeekWeather?>?> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(apiHelper.getWeekWeather(city, employeeId, units)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}