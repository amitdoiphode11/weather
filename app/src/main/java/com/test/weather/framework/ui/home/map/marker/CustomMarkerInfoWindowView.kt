package com.test.weather.framework.ui.home.map.marker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.test.weather.R
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import com.test.weather.framework.ui.home.map.marker.adapter.WeekWeatherListAdapter
import kotlinx.android.synthetic.main.item_info_window.view.*

class CustomMarkerInfoWindowView(
    private val context: Context?,
    private val cityName: String?,
    private val weWeekWeather: WeWeekWeather
) :
    InfoWindowAdapter, WeekWeatherListAdapter.OutletItemClickListener {
    private val markerItemView: View
    private var adapter: WeekWeatherListAdapter? = null

    override fun getInfoWindow(marker: Marker): View { // 2

        adapter = WeekWeatherListAdapter(context, this)
        markerItemView.rv_weather.layoutManager = LinearLayoutManager(context)
        markerItemView.rv_weather.adapter = adapter
        markerItemView.tv_name.text = cityName
        adapter?.setList(weWeekWeather.list)

        return markerItemView // 4
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    init {
        markerItemView =
            LayoutInflater.from(context).inflate(R.layout.item_info_window, null) // 1
    }

    override fun onItemClick(weCurrentWeather: WeCurrentWeather?, view: View?) {

    }

    override fun onRetryClick() {

    }

}