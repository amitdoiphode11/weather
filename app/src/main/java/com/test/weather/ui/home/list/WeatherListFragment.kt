package com.test.weather.ui.home.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.weather.R
import com.test.weather.data.api.ApiHelperImpl
import com.test.weather.data.api.RetrofitBuilder
import com.test.weather.data.model.WeCurrentWeather
import com.test.weather.data.reporsitory.WeatherRepository
import com.test.weather.ui.base.BaseFragmentKotlin
import com.test.weather.ui.home.ViewModelFactory
import com.test.weather.ui.home.list.adapter.CityWeatherListAdapter
import com.test.weather.utils.api.Status
import kotlinx.android.synthetic.main.weather_list_fragment.*

class WeatherListFragment : BaseFragmentKotlin(),
    CityWeatherListAdapter.OutletItemClickListener {

    private var adapter: CityWeatherListAdapter? = null
    private var viewModel: WeatherListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.weather_list_fragment
    }

    override fun initView(view: View) {
        setUI()
        initViewModel()
        apiCall()
    }

    private fun setUI() {
        adapter = CityWeatherListAdapter(activity, this)
        rv_weather.adapter = adapter
        rv_weather.layoutManager = LinearLayoutManager(activity)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(WeatherRepository(ApiHelperImpl(RetrofitBuilder.apiService)))
            )
                .get(WeatherListViewModel::class.java)
    }


    private fun apiCall() {
        viewModel?.fetchWeather()
        viewModel?.getWeather()?.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    it.data?.let { users -> renderList(users) }
                    rv_weather.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    showLoading()
                    rv_weather.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    hideLoading()
                    showError(it.message)
                }
            }
        })
    }

    private fun renderList(weather: ArrayList<WeCurrentWeather>) {
        adapter?.setList(weather)
        adapter?.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onItemClick(weCurrentWeather: WeCurrentWeather?, view: View?) {

    }

    override fun onRetryClick() {

    }


}
