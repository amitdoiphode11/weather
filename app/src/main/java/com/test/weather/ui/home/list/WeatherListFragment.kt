package com.test.weather.ui.home.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.test.weather.R
import com.test.weather.data.WeCurrentWeather
import com.test.weather.ui.base.BaseFragmentKotlin
import com.test.weather.ui.home.SharedViewModel
import com.test.weather.ui.home.list.adapter.CityWeatherListAdapter
import kotlinx.android.synthetic.main.weather_list_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WeatherListFragment : BaseFragmentKotlin(), CoroutineScope,
    CityWeatherListAdapter.OutletItemClickListener {

    companion object {
        fun newInstance() = WeatherListFragment()
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val ioContext: CoroutineContext
        get() = Dispatchers.IO + job

    private lateinit var job: Job

    private lateinit var adapter: CityWeatherListAdapter
    private lateinit var viewModel: WeatherListViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.weather_list_fragment
    }

    override fun initView(view: View) {
        viewModel = ViewModelProviders.of(this).get(WeatherListViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        adapter = CityWeatherListAdapter(activity, this)
        rv_weather.adapter = adapter
        rv_weather.layoutManager = LinearLayoutManager(activity)
        launch {
            sharedViewModel.getCityCurrentWeather()
                .observe(this@WeatherListFragment, Observer { weCurrentList ->
                    if (weCurrentList.isEmpty()) {
                        showError(R.string.default_error)
                    } else {
                        adapter.setList(weCurrentList)
                    }
                })
        }


    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onItemClick(weCurrentWeather: WeCurrentWeather?, view: View?) {

    }

    override fun onRetryClick() {

    }


}
