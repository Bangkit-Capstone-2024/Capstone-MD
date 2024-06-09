package com.cpstn.momee.ui.register

import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.cpstn.momee.databinding.ActivityRegisterBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    override fun setupView() {

        setupObserver()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        btnMoveLogin.setOnClickListener {
            startActivityTo(LoginActivity::class.java)
        }
        btnRegister.setOnClickListener {
            viewModel.register(binding.etUsername.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun setupObserver() {
        viewModel.registerDataResult.observe(this) {
            when (it) {
                is DataResult.Loading -> {
                    binding.pbLoading.isVisible = true
                }
                is DataResult.Error -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this, "Register Error", Toast.LENGTH_SHORT).show()
                }
                is DataResult.Success -> {
                    binding.pbLoading.isVisible = false
                    startActivityTo(LoginActivity::class.java)
                    Toast.makeText(this, "Register Success with login info name ${it.data?.data?.username}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}