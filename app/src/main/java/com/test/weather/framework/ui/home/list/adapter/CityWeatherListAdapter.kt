package com.test.weather.framework.ui.home.list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.weather.R
import com.test.weather.business.data.model.WeCurrentWeather
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_weather.view.*
import java.util.*

class CityWeatherListAdapter(
    private val mContext: Context?,
    val mListener: OutletItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private val weatherAdapterList: MutableList<WeCurrentWeather?>
    private val VIEW_TYPE_DATA = 1
    private val VIEW_TYPE_LOADING = 2
    private val VIEW_TYPE_RETRY = 3

    init {
        weatherAdapterList = ArrayList()
    }

    fun setList(WeCurrentWeathers: List<WeCurrentWeather?>?) {
        weatherAdapterList.clear()
        weatherAdapterList.addAll(WeCurrentWeathers!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_DATA -> {
                val v1 = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather, parent, false)
                return OutletViewHolder(v1)
            }
            else -> {
                val v1 = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weather, parent, false)
                return OutletViewHolder(v1)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OutletViewHolder) {
            holder.onBind(
                weatherAdapterList[position]
            )
        } /* else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).onBind(mWeCurrentWeatherAdapterList.get(position));
        } else if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).onBind(mWeCurrentWeatherAdapterList.get(position));
        }*/
    }

    fun addLoaderElement() {
        if (weatherAdapterList.isEmpty() || !weatherAdapterList[itemCount - 1]!!.isLoader!!) {
            val lastPosition = itemCount
            val weCurrentWeather = WeCurrentWeather()
            weCurrentWeather.isLoader = true
            weatherAdapterList.add(lastPosition, weCurrentWeather)
            notifyItemInserted(lastPosition)
        }
    }

    fun removeLoaderElement() {
        if (!weatherAdapterList.isEmpty() && weatherAdapterList[itemCount - 1]!!.isLoader!!) {
            val lastPosition = itemCount - 1
            weatherAdapterList.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    fun addRetryElement() {
        if (weatherAdapterList.isEmpty() || !weatherAdapterList[itemCount - 1]!!.isRetry!!) {
            val lastPosition = itemCount
            val WeCurrentWeather = WeCurrentWeather()
            WeCurrentWeather.isRetry = true
            weatherAdapterList.add(lastPosition, WeCurrentWeather)
            notifyItemInserted(lastPosition)
        }
    }

    fun removeRetryElement() {
        if (!weatherAdapterList.isEmpty() && weatherAdapterList[itemCount - 1]!!.isRetry!!) {
            val lastPosition = itemCount - 1
            weatherAdapterList.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    override fun getItemCount(): Int {
        return weatherAdapterList.size
    }

    override fun getItemViewType(position: Int): Int {
        /*return if (weatherAdapterList[position] == null || weatherAdapterList[position]?.isLoader!!
        ) {
            VIEW_TYPE_LOADING
        } else if (weatherAdapterList[position]!!.isRetry!!) {
            VIEW_TYPE_RETRY
        } else {
            VIEW_TYPE_DATA
        }*/
        return VIEW_TYPE_DATA
    }

    internal inner class OutletViewHolder(var view: View) : RecyclerView.ViewHolder(view),
        LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun onBind(weCurrentWeather: WeCurrentWeather?) {
            view.tv_name.text = weCurrentWeather?.name
            view.tv_description.text = weCurrentWeather?.weather?.get(0)?.description
            view.tv_temp_max.text = weCurrentWeather?.main?.temp_max.toString()
            view.tv_temp_min.text = weCurrentWeather?.main?.temp_min.toString()
        }

        override val containerView: View?
            get() = view
    }

    interface OutletItemClickListener {
        fun onItemClick(
            weCurrentWeather: WeCurrentWeather?,
            view: View?
        )

        fun onRetryClick()
    }


}