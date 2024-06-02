package com.cpstn.momee.ui.login

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.cpstn.momee.databinding.ActivityLoginBinding
import com.cpstn.momee.network.Result
import com.cpstn.momee.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun setupView() {
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.login().observe(this) {
            when (it) {
                is Result.Loading -> {}
                is Result.Error -> {}
                is Result.Success -> {
                    Toast.makeText(this, "Login Success with login info name ${it.data?.data?.username}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}