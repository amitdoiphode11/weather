package com.test.weather.framework.ui.home.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.business.domain.utils.state.DataState
import com.test.weather.business.interactors.GetWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class WeatherListViewModel
@ViewModelInject
constructor(
    private val getWeather: GetWeather?
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

        object None : MainStateEvent()
    }
}
