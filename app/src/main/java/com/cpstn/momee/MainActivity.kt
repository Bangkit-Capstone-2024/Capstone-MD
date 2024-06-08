package com.cpstn.momee

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cpstn.momee.databinding.ActivityMainBinding
import com.cpstn.momee.utils.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupView() {
        setupBottomNav()
    }

    private fun setupBottomNav() = with(binding) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
    }
}