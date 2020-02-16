package com.test.weather.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val TAG = "ApiClient"
    private const val TIMEOUT_PERIOD = 120
    private var apiService: ApiService? = null

    private fun getRetrofitInstance(baseUrl: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        Log.e(TAG, "getRetrofitInstance: $baseUrl")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getNormalOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getNormalOkHttpClient(): OkHttpClient? {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(
                TIMEOUT_PERIOD.toLong(),
                TimeUnit.SECONDS
            ) //Sets the default read timeout for new connections
            .writeTimeout(
                TIMEOUT_PERIOD.toLong(),
                TimeUnit.SECONDS
            ) //set the default write timeout for new connections
            .connectTimeout(
                TIMEOUT_PERIOD.toLong(),
                TimeUnit.SECONDS
            ) //Sets the default connect timeout for new connections.
        /*log network call*///okHttpClientBuilder.addInterceptor(RetrofitUtils.getLoggingLevel)
        return okHttpClientBuilder.build()
    }

    fun getApi(): ApiService? {
        if (apiService == null) {
            val baseUrl = "http://api.openweathermap.org/data/2.5/"
            val retrofit: Retrofit = getRetrofitInstance(baseUrl)
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }


}