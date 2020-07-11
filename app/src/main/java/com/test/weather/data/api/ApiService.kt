package com.test.weather.data.api

import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.model.WeWeekWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //F = imperial
    //C = metric
    @GET("weather/")
    suspend fun getCurrentWeather(
        @Query("q") city: String?,
        @Query("appid") employeeId: String?,
        @Query("units") units: String? = "metric"
    ): WeCurrentWeather?


    @GET("forecast/")
    suspend fun getWeekWeather(
        @Query("q") city: String?,
        @Query("appid") employeeId: String?,
        @Query("units") units: String? = "metric"
    ): WeWeekWeather?

}