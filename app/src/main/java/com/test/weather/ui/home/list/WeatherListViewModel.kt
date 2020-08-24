package com.test.weather.ui.home.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.reporsitory.WeatherRepositoryImpl
import com.test.weather.utils.state.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class WeatherListViewModel
@ViewModelInject
constructor(
    private val weatherRepositoryImpl: WeatherRepositoryImpl?
) : ViewModel() {

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


    fun setState(mainStateEvent: MainStateEvent?) {
        when (mainStateEvent) {
            is MainStateEvent.GetWeatherList -> {
                viewModelScope.launch {
                    val tempList: MutableList<WeCurrentWeather?> = arrayListOf()
                    for (city in cityList) {
                        weatherRepositoryImpl?.getCurrentWeather(city, appId)
                            ?.onEach {
                                when (it) {
                                    is DataState.Success -> {
                                        tempList.add(it.data)
                                    }
                                }

                            }
                            ?.launchIn(viewModelScope)
                    }
                    _weatherList.postValue(DataState.Success(tempList))
                }
            }
        }

    }

    sealed class MainStateEvent {

        object GetWeatherList : MainStateEvent()

        object None : MainStateEvent()
    }
}
