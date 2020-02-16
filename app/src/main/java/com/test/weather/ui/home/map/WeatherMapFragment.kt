package com.test.weather.ui.home.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.test.weather.R
import com.test.weather.data.WeCurrentWeather
import com.test.weather.ui.base.BaseFragmentKotlin
import com.test.weather.ui.home.SharedViewModel
import com.test.weather.ui.home.map.marker.CustomMarkerInfoWindowView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext


class WeatherMapFragment : BaseFragmentKotlin(), CoroutineScope, OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    companion object {
        fun newInstance() = WeatherMapFragment()
    }

    private var cityList: ArrayList<WeCurrentWeather>? = ArrayList()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val ioContext: CoroutineContext
        get() = Dispatchers.IO + job

    private lateinit var job: Job

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: WeatherMapViewModel
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.weather_map_fragment
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun initView(view: View) {
        initViewModel()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_city) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(WeatherMapViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

        launch {
            //City weather list
            sharedViewModel.getCityCurrentWeather()
                .observe(this@WeatherMapFragment, Observer { weCurrentList ->
                    if (weCurrentList.isEmpty()) {
                        showError(R.string.default_error)
                    } else {
                        //Set Map marker
                        cityList = weCurrentList
                        setMarker(map)
                    }
                })


        }
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

    private fun moveCamera(mMap: GoogleMap?) = launch(coroutineContext) {
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

    private fun setMarker(mMap: GoogleMap?) = launch(coroutineContext) {
        for (weCurrentWeather in cityList!!) {
            mMap?.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            weCurrentWeather?.coord?.lat!!,
                            weCurrentWeather?.coord?.lon!!
                        )
                    )
                    .title(weCurrentWeather.name)
                //.icon(activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_place) })
            )


        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        launch(coroutineContext) {

            //Week weather
            sharedViewModel.getWeekWeather(marker?.title).observe(this@WeatherMapFragment,
                Observer { weWeekWeather ->
                    if (weWeekWeather == null) {
                        showError(R.string.default_error)
                    } else {
                        map?.setInfoWindowAdapter(
                            CustomMarkerInfoWindowView(
                                activity,
                                marker?.title,
                                weWeekWeather
                            )
                        )
                    }
                })
        }
        return false
    }


}
