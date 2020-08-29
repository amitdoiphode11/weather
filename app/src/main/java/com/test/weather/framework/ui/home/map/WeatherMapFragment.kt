package com.test.weather.framework.ui.home.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.test.weather.R
import com.test.weather.business.domain.utils.state.DataState
import com.test.weather.framework.datasource.network.model.WeCurrentWeather
import com.test.weather.framework.datasource.network.model.WeWeekWeather
import com.test.weather.framework.ui.home.map.marker.CustomMarkerInfoWindowView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_list_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.StringBuilder
import java.util.ArrayList


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherMapFragment constructor(
    private val someString: String
) : Fragment(R.layout.weather_map_fragment), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    companion object {
        private const val TAG = "WeatherMapFragment"
    }

    private var cityList: ArrayList<WeCurrentWeather>? = ArrayList()

    private val viewModel: WeatherMapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setState(WeatherMapViewModel.MainStateEvent.GetWeatherList)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_city) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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

    private fun subscribeWeekWeatherObservers(marker: Marker?) {
        viewModel.getWeekWeather().observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<*> -> {
                    displayProgressBar(false)
                    setWeekWeatherList(marker, dataState.data as WeWeekWeather?)
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

        //Set Map marker
        cityList = list as ArrayList<WeCurrentWeather>
        setMarker(map)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMapReady(mMap: GoogleMap?) {
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.clear() //clear old markers
        if (mMap != null) {
            map = mMap
        }
        mMap?.setOnMarkerClickListener(this@WeatherMapFragment)
        //Move camera position
        moveCamera(mMap)
        setMarker(mMap)
    }

    @SuppressLint("MissingPermission")
    private fun moveCamera(mMap: GoogleMap?) {
        var lastLocation: Location? = null
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            lastLocation = location
        }
        val latLng: LatLng
        if (lastLocation == null) {
            latLng = LatLng(24.017641, 79.480401)
        } else {
            latLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
        }
        val moveToCurrentLocation = CameraPosition.builder()
            .target(latLng)
            .zoom(5f)
            .bearing(0f)
            .tilt(45f)
            .build()

        mMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(moveToCurrentLocation),
            10000,
            null
        )
    }

    private fun setMarker(mMap: GoogleMap?) {
        for (weCurrentWeather in cityList!!) {
            mMap?.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            weCurrentWeather.coord?.lat!!,
                            weCurrentWeather.coord?.lon!!
                        )
                    )
                    .title(weCurrentWeather.name)
                //.icon(activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_place) })
            )


        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        //Week weather
        viewModel.setWeekStateEvent(
            WeatherMapViewModel.MainStateEvent.GetWeekWeatherList,
            marker?.title
        );
        subscribeWeekWeatherObservers(marker)
        return false
    }

    private fun setWeekWeatherList(
        marker: Marker?,
        weWeekWeather: WeWeekWeather?
    ) {
        map.setInfoWindowAdapter(
            weWeekWeather?.let {
                CustomMarkerInfoWindowView(
                    activity,
                    marker?.title,
                    weWeekWeather
                )
            }
        )
    }


}
