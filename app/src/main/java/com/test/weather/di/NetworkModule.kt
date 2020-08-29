package com.test.weather.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.weather.business.data.network.WeatherDataSourceImpl
import com.test.weather.framework.datasource.network.ApiHelper
import com.test.weather.framework.datasource.network.ApiHelperImpl
import com.test.weather.framework.datasource.network.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    /* @Singleton
     @Provides
     fun provideNetworkMapper(): EntityMapper<BlogNetworkEntity, Blog>{
         return NetworkMapper()
     }
 */
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        run {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        apiService: ApiService
    ): ApiHelper {
        return ApiHelperImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        apiHelper: ApiHelper
    ): WeatherDataSourceImpl {
        return WeatherDataSourceImpl(apiHelper)
    }

}




















