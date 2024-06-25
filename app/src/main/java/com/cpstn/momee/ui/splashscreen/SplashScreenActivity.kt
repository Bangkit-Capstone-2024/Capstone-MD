package com.cpstn.momee.ui.splashscreen

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cpstn.momee.MainActivity
import com.cpstn.momee.databinding.ActivitySplashScreenBinding
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.ui.onboarding.OnBoardingActivity
import com.cpstn.momee.ui.onboarding.OnboardingViewModel
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun getViewBinding(): ActivitySplashScreenBinding =
        ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun setupView() {
        lifecycleScope.launch {
            delay(1000)
            setupObserver()
        }
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            when {
                it.userToken.isEmpty() && it.hasShowOnboarding -> goToLogin()
                it.userToken.isNotEmpty() && it.hasShowOnboarding -> goToMain()
                else -> goToOnboarding()
            }
        }
    }

    private fun goToLogin() {
        startActivityTo(LoginActivity::class.java)
        finish()
    }

    private fun goToMain() {
        startActivityTo(MainActivity::class.java)
        finish()
    }

    private fun goToOnboarding() {
        startActivityTo(OnBoardingActivity::class.java)
        finish()
    }

}