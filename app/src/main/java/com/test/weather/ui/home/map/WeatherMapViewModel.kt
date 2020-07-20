package com.test.weather.ui.home.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.data.api.ApiHelper
import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.model.WeWeekWeather
import com.test.weather.data.reporsitory.WeatherRepository
import com.test.weather.ui.home.list.WeatherListViewModel
import com.test.weather.utils.api.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherMapViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository?) :
    ViewModel() {
    companion object {
        private val appId = "4017c85936fc42617cff4bc115dd2214"
        val cityList: ArrayList<String> = arrayListOf(
            "Mumbai",
            "Delhi",
            "Kolkata",
            "Chennai",
            "Bangalore",
            "Hyderabad",
            "Ahmedabad",
            "Pune",
            "Surat",
            "Jaipur"
        )
    }


    private val weekWeatherList = MutableLiveData<Resource<WeWeekWeather>>()
    private val weatherList = MutableLiveData<Resource<ArrayList<WeCurrentWeather>>>()

    fun fetchWeekWeather(city: String?) {
        viewModelScope.launch {
            weekWeatherList.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val weatherResult =
                        async { weatherRepository?.getWeekWeather(city, appId) }.await()
                    weekWeatherList.postValue(Resource.success(weatherResult))
                }
            } catch (e: Exception) {
                weekWeatherList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            weatherList.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val tempList = arrayListOf<WeCurrentWeather>()
                    for (city in WeatherListViewModel.cityList) {
                        val weatherResult =
                            async { weatherRepository?.getCurrentWeather(city, appId) }.await()
                        weatherResult?.let { tempList.add(it) }
                    }

                    weatherList.postValue(Resource.success(tempList))
                }
            } catch (e: Exception) {
                weatherList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }


    fun getWeekWeather(): LiveData<Resource<WeWeekWeather>> {
        return weekWeatherList
    }


    fun getWeather(): LiveData<Resource<ArrayList<WeCurrentWeather>>> {
        return weatherList
    }

}
