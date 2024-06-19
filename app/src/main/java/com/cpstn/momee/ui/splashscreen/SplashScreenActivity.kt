package com.cpstn.momee.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivitySplashScreenBinding
import com.cpstn.momee.ui.onboarding.OnBoardingActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override fun getViewBinding(): ActivitySplashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun setupView() {

        lifecycleScope.launch {
            delay(1000)
            startActivityTo(OnBoardingActivity::class.java)
            finish()
        }
    }

}