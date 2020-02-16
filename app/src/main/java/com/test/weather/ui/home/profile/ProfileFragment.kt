package com.test.weather.ui.home.profile

import androidx.lifecycle.ViewModelProviders
import android.view.View
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth

import com.test.weather.R
import com.test.weather.ui.base.BaseFragmentKotlin
import com.test.weather.ui.home.SharedViewModel
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragmentKotlin(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var sharedViewModel: SharedViewModel
    override fun getFragmentLayout(): Int {
        return R.layout.profile_fragment
    }

    override fun initView(view: View) {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        setUserDetails()
    }

    private fun setUserDetails() {
        tv_name.text = FirebaseAuth.getInstance().currentUser?.displayName
        tv_mobile.text = FirebaseAuth.getInstance().currentUser?.phoneNumber
        tv_list.setOnClickListener(this)
        tv_map.setOnClickListener(this)
        rg_unit.setOnCheckedChangeListener(this)
        getCheckedUnit(rg_unit.checkedRadioButtonId)

    }

    private fun getCheckedUnit(checkedId: Int) {
        when (checkedId) {
            R.id.rb_metric -> {
                sharedViewModel.setUnit(getString(R.string.label_metric))
            }
            R.id.rb_imperial -> {
                sharedViewModel.setUnit(getString(R.string.label_imperial))
            }
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_list -> {
                sharedViewModel.setTab("list")
            }
            R.id.tv_map -> {
                sharedViewModel.setTab("map")
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        getCheckedUnit(checkedId)
    }


}
