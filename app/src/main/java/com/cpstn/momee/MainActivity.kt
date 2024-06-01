package com.cpstn.momee

import com.cpstn.momee.databinding.ActivityMainBinding
import com.cpstn.momee.utils.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupView() {
        with(binding) {
            tvHello.text = getString(R.string.app_name)
        }
    }
}