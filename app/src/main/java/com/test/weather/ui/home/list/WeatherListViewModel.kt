package com.test.weather.ui.home.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.data.api.ApiHelper
import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.utils.api.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherListViewModel(private val apiHelper: ApiHelper?) : ViewModel() {

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


    private val weatherList = MutableLiveData<Resource<ArrayList<WeCurrentWeather>>>()

    fun fetchWeather() {
        viewModelScope.launch {
            weatherList.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val tempList = arrayListOf<WeCurrentWeather>()
                    for (city in cityList) {
                        val weatherResult =
                            async { apiHelper?.getCurrentWeather(city, appId) }.await()
                        weatherResult?.let { tempList.add(it) }
                    }

                    weatherList.postValue(Resource.success(tempList))
                }
            } catch (e: Exception) {
                weatherList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getWeather(): LiveData<Resource<ArrayList<WeCurrentWeather>>> {
        return weatherList
    }

}
