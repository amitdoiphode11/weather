package com.test.weather.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.test.weather.R
import com.test.weather.ui.home.HomeActivity
import com.test.weather.ui.login.LoginActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val ioContext: CoroutineContext
        get() = Dispatchers.IO + job

    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        job = Job()

        launch {
            delay(3000)
            goToDashboard()
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun goToDashboard() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            //Dashboard
            intent = HomeActivity.getStartIntent(this)
        } else {
            intent = LoginActivity.getStartIntent(this)
        }
        startActivity(intent)
        finish()
    }


}
