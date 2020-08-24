package com.test.weather.framework.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.test.weather.framework.ui.home.list.WeatherListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
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

            else -> super.instantiate(classLoader, className)
        }
    }
}