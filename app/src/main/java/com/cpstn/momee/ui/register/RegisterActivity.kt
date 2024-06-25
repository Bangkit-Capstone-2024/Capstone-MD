package com.cpstn.momee.ui.register

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivityRegisterBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.hideKeyboard
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun setupView() {

        setupObserver()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        val text = "Sudah punya akun? "
        val clickableText = "Masuk disini"
        val spannableString = SpannableString(text + clickableText)
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                finish()
            }
        }

        val start = text.length
        val end = (text + clickableText).length
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@RegisterActivity,
                    R.color.color_primary
                )
            ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvLogin.highlightColor = Color.TRANSPARENT
        binding.tvLogin.text = spannableString
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()
        btnRegister.setOnClickListener {
            hideKeyboard(this@RegisterActivity)
            viewModel.register(
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
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
                    Toast.makeText(
                        this,
                        "Register Success with login info name ${it.data?.data?.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}