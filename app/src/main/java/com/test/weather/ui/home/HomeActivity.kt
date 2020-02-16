package com.test.weather.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.test.weather.R
import com.test.weather.ui.base.BaseActivityKotlin
import com.test.weather.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivityKotlin() {
    private var sharedViewModel: SharedViewModel? = null
    private lateinit var navController: NavController

    companion object {
        private val TAG = "HomeActivity"
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    override fun init() {
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        setSupportActionBar(toolbar)

        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //Setting the navigation controller to Bottom Nav
        bottomNav.setupWithNavController(navController)


        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController)


        sharedViewModel?.tabSelectedListener?.observe(this, Observer {
            when (it) {
                "list" ->
                    bottomNav.selectedItemId = R.id.menu_bottom_home
                "map" ->
                    bottomNav.selectedItemId = R.id.menu_bottom_map
            }
        })
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                FirebaseAuth.getInstance().signOut()
                intent = LoginActivity.getStartIntent(this)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
