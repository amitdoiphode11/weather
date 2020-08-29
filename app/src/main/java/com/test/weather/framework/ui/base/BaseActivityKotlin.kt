package com.test.weather.framework.ui.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.test.weather.R
import com.test.weather.business.domain.utils.NetworkUtils

abstract class BaseActivityKotlin : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    /*override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
        print("$TAG attachBaseContext")
    }*/

    /**
     * function to initialize data
     */
    abstract fun init()

    /**
     * function to show loading
     */
    abstract fun showLoading()

    /**
     * function to hide loading
     */
    abstract fun hideLoading()

    private fun resetTitles() {
        try {
            val info = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            print("$TAG resetTitles: $e")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        hideKeyboard()
    }


    fun showError(message: String?) {
        print("$TAG Message")
        if (message != null) {
            showSnackBar(message, R.color.colorErrorRed)
        } else {
            showSnackBar(getString(R.string.default_error), R.color.colorErrorRed)
        }
    }


    fun showError(message: CharSequence?) {
        if (message != null) {
            showSnackBar(message.toString(), R.color.colorErrorRed)
        } else {
            showSnackBar(getString(R.string.default_error), R.color.colorErrorRed)
        }
    }


    fun showToast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun showError(@StringRes resId: Int) {
        showError(getString(resId))
    }


    fun showInternetError() {
        showError(R.string.snackbar_internet_unavailable)
    }

    fun showSnackBar(message: String) {
        showSnackBar(message, R.color.colorWhite)
    }


    fun showSnackBarForLongPeriod(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }


    fun showSnackBar(message: String, @ColorRes textColor: Int) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, textColor))
        snackbar.show()
    }

    // public abstract void handleBackFromToolBar();


    fun showSnackBar(@StringRes resId: Int) {
        showSnackBar(getString(resId))
    }


    fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}