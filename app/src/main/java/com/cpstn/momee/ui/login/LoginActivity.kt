package com.cpstn.momee.ui.login

import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.cpstn.momee.MainActivity
import com.cpstn.momee.databinding.ActivityLoginBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun setupView() {

        viewModel.getUserSession()
        setupObserver()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        btnLogin.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            if (it.userToken.isNotEmpty()) {
                startActivityTo(MainActivity::class.java)
            }
        }
        viewModel.loginDataResult.observe(this) {
            when (it) {
                is DataResult.Loading -> {
                    binding.pbLoading.isVisible = true
                }

                is DataResult.Error -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show()
                }

                is DataResult.Success -> {
                    binding.pbLoading.isVisible = false
                    viewModel.saveUserSession(
                        it.data?.data?.token.orEmpty(),
                        it.data?.data?.username.orEmpty(),
                        it.data?.data?.email.orEmpty()
                    )
                    startActivityTo(MainActivity::class.java)
                    Toast.makeText(
                        this,
                        "Login Success with login info name ${it.data?.data?.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}