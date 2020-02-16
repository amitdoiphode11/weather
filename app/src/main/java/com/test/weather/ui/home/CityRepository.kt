package com.test.weather.ui.home

import androidx.lifecycle.MutableLiveData
import com.test.weather.data.WeCurrentWeather
import com.test.weather.data.WeWeekWeather
import com.test.weather.network.ApiClient
import retrofit2.Response

class CityRepository {
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

    private var currentWeatherList: MutableLiveData<ArrayList<WeCurrentWeather>> = MutableLiveData()
    private var weekWeatherList: MutableLiveData<WeWeekWeather> = MutableLiveData()

    //Call after 3hr (WorkManager)

    suspend fun getCityWeather(units: String?): MutableLiveData<ArrayList<WeCurrentWeather>> {
        var response: Response<WeCurrentWeather?>? = null
        val tempList: ArrayList<WeCurrentWeather>? = ArrayList()
        for (city in cityList) {
            response = ApiClient.getApi()?.getCurrentWeather(city, appId, units)
            if (response != null) {
                response.body()?.let { tempList?.add(it) }
            }
        }
        currentWeatherList?.value = tempList
        return currentWeatherList
    }

    suspend fun getCityWeekWeather(city: String?, units: String?): MutableLiveData<WeWeekWeather> {
        val response = ApiClient.getApi()?.getWeekWeather(city, appId, units)
        if (response != null) {
            weekWeatherList.value = response.body()
        }
        return weekWeatherList
    }
}