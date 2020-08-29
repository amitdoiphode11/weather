package com.test.weather.di

import com.test.weather.business.data.network.WeatherDataSource
import com.test.weather.business.interactors.GetWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object IntegratorsModule {


    @Provides
    fun provideGetWeather(
        weatherDataSource: WeatherDataSource
    ): GetWeather {
        return GetWeather(weatherDataSource)
    }
}














