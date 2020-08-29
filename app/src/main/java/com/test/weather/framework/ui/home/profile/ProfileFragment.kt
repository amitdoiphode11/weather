package com.test.weather.framework.ui.home.profile

import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

import com.test.weather.R
import com.test.weather.framework.ui.base.BaseFragmentKotlin
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragmentKotlin(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    override fun getFragmentLayout(): Int {
        return R.layout.profile_fragment
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
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
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        getCheckedUnit(checkedId)
    }


}
