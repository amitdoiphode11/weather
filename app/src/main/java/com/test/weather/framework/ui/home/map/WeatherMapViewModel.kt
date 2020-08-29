package com.test.weather.framework.ui.home.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import com.test.weather.business.domain.utils.state.DataState
import com.test.weather.business.interactors.GetWeather
import com.test.weather.framework.ui.home.list.WeatherListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherMapViewModel @ViewModelInject constructor(private val getWeather: GetWeather?) :
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

    private val _weatherList = MutableLiveData<DataState<MutableList<WeCurrentWeather?>?>>()
    fun getWeather(): LiveData<DataState<MutableList<WeCurrentWeather?>?>> {
        return _weatherList
    }

    private val _weekWeatherList = MutableLiveData<DataState<WeWeekWeather?>>()
    fun getWeekWeather(): LiveData<DataState<WeWeekWeather?>> {
        return _weekWeatherList
    }

    fun setWeekStateEvent(mainStateEvent: MainStateEvent?, city: String?) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetWeekWeatherList -> {
                    getWeather?.getWeekWeather(city, appId)
                        ?.onEach { dataState -> _weekWeatherList.value = dataState }
                        ?.launchIn(viewModelScope)
                }
            }
        }
    }

    fun setState(mainStateEvent: MainStateEvent?) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetWeatherList -> {
                    val tempList: MutableList<WeCurrentWeather?> = arrayListOf()
                    for (city in cityList) {
                        getWeather?.getCurrentWeather(city, appId)
                            ?.onEach {
                                when (it) {
                                    is DataState.Success -> {
                                        tempList.add(it.data)
                                    }
                                }
                            }?.launchIn(viewModelScope)
                    }
                    _weatherList.postValue(DataState.Success(tempList))

                }
            }
        }
    }

    sealed class MainStateEvent {

        object GetWeatherList : MainStateEvent()

        object GetWeekWeatherList : MainStateEvent()

        object None : MainStateEvent()
    }
}
