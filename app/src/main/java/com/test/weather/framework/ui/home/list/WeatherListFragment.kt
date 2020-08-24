package com.test.weather.framework.ui.home.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.test.weather.R
import com.test.weather.business.data.model.WeCurrentWeather
import com.test.weather.framework.ui.home.list.adapter.CityWeatherListAdapter
import com.test.weather.business.utils.state.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_list_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.StringBuilder


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherListFragment constructor(
    private val someString: String
) : Fragment(R.layout.weather_list_fragment),
    CityWeatherListAdapter.OutletItemClickListener {

    private val adapter: CityWeatherListAdapter? = null
    private val viewModel: WeatherListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setState(WeatherListViewModel.MainStateEvent.GetWeatherList)

        Log.d(TAG, "someString: ${someString}")
    }

    private fun subscribeObservers() {
        viewModel.getWeather().observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<*> -> {
                    displayProgressBar(false)
                    setWeatherList(dataState.data as List<WeCurrentWeather>)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Unknown error.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setWeatherList(list: List<WeCurrentWeather>) {
        val sb = StringBuilder()
        for (blog in list) {
            sb.append(blog.name + "\n")
        }
        Log.e(TAG, "setWeatherList: $sb")
        //text.text = sb.toString()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onItemClick(weCurrentWeather: WeCurrentWeather?, view: View?) {

    }

    override fun onRetryClick() {

    }

    companion object {
        private const val TAG = "WeatherListFragment"
    }


}
