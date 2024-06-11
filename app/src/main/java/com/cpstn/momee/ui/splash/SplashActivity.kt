package com.cpstn.momee.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cpstn.momee.MainActivity
import com.cpstn.momee.R
import com.cpstn.momee.ui.start.FirstStartActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser != null) {
                MainActivity.start(this@SplashActivity)
            }else{
                FirstStartActivity.start(this@SplashActivity)
            }
        }, 3000)



    }
}