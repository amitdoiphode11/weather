package com.test.weather.framework.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.test.weather.framework.ui.home.list.WeatherListFragment
import com.test.weather.framework.ui.home.map.WeatherMapFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FragmentFactory
@Inject
constructor(
    private val someString: String
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            WeatherListFragment::class.java.name -> {
                val fragment = WeatherListFragment(someString)
                fragment
            }

            WeatherMapFragment::class.java.name -> {
                val fragment = WeatherMapFragment(someString)
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}