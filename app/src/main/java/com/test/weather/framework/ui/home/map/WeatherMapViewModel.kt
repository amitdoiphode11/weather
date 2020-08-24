package com.test.weather.framework.ui.home.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.business.data.model.WeCurrentWeather
import com.test.weather.business.data.model.WeWeekWeather
import com.test.weather.business.data.reporsitory.WeatherRepositoryImpl
import com.test.weather.business.utils.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherMapViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepositoryImpl?) :
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


    private val _weekWeatherList = MutableLiveData<DataState<WeWeekWeather?>>()
    private val _weatherList = MutableLiveData<DataState<ArrayList<WeCurrentWeather>>>()

/*
    fun fetchWeekWeather(city: String?) {
        viewModelScope.launch {
            */
/*_weekWeatherList.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val weatherResult =
                        async { weatherRepositoryImpl?.getWeekWeather(city, appId) }.await()
                    _weekWeatherList.postValue(Resource.success(weatherResult))
                }
            } catch (e: Exception) {
                _weekWeatherList.postValue(Resource.error("Something Went Wrong", null))
            }*//*

        }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            _weatherList.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val tempList = arrayListOf<WeCurrentWeather>()
                    for (city in WeatherListViewModel.cityList) {
                        val weatherResult =
                            async { weatherRepository?.getCurrentWeather(city, appId) }.await()
                        weatherResult?.let { tempList.add(it) }
                    }

                    _weatherList.postValue(Resource.success(tempList))
                }
            } catch (e: Exception) {
                _weatherList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }
*/


    fun getWeekWeather(): LiveData<DataState<WeWeekWeather?>> {
        return _weekWeatherList
    }


    fun getWeather(): LiveData<DataState<ArrayList<WeCurrentWeather>>> {
        return _weatherList
    }

    fun setStateEvent(mainStateEvent: MainStateEvent?, city: String?) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetWeekWeatherList -> {
                    weatherRepository?.getWeekWeather(city, appId)
                        ?.onEach { dataState -> _weekWeatherList.value = dataState }
                        ?.launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class MainStateEvent {

        object GetWeekWeatherList : MainStateEvent()

        object None : MainStateEvent()
    }
}
