package com.test.weather.framework.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.test.weather.R
import com.test.weather.business.utils.NetworkUtils

abstract class BaseFragmentKotlin : Fragment() {


    @LayoutRes
    abstract fun getFragmentLayout(): Int

    abstract fun initView(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    fun showError(message: String?) {
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

    fun showError(@StringRes resId: Int) {
        showError(getString(resId))
    }

    fun showInternetError() {
        showError(R.string.snackbar_internet_unavailable)
    }

    fun showToast(@StringRes resId: Int) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    fun showSnackBar(message: String) {
        showSnackBar(message, R.color.colorWhite)
    }

    fun showSnackBar(message: String, @ColorRes textColor: Int) {
        if (activity != null && context != null && isAdded) {
            val snackbar = Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
            )
            val sbView = snackbar.view
            val textView =
                sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(ContextCompat.getColor(context!!, textColor))
            snackbar.show()
        }
    }

    fun showSnackBar(@StringRes resId: Int) {
        showSnackBar(getString(resId))
    }

    fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkConnected(context?.applicationContext)
    }

    fun hideKeyboard() {
        if (activity != null) {
            val view = activity?.currentFocus
            if (view != null) {
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    abstract fun showLoading()
    abstract fun hideLoading()
}