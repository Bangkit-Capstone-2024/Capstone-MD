package com.cpstn.momee.ui.login

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.cpstn.momee.MainActivity
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivityLoginBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.register.RegisterActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.hideKeyboard
import com.cpstn.momee.utils.startActivityTo
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun setupView() {

        viewModel.getUserSession()
        setupObserver()
        setupListener()
    }

    private fun setupGoogleSignIn() {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("681901416946-38dlu7dbdvr27q84qeq8qkk5um4thjrd.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(this)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("zxc", "handleFailure:", e)
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        viewModel.loginGoogle(googleIdTokenCredential.idToken)
                        Log.i("zxc", "handleSignIn: ${googleIdTokenCredential.idToken}")
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("zxc", "handleSignIn:", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("zxc", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("zxc", "Unexpected type of credential")
            }
        }
    }

    private fun setupListener() = with(binding) {
        setupClickableText()
        btnLogin.setOnClickListener {
            hideKeyboard(this@LoginActivity)
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        btnSignInWithGoogle.setOnClickListener {
            setupGoogleSignIn()
        }
    }

    private fun setupClickableText() {
        val text = "Belum punya akun? "
        val clickableText = "Daftar disini"
        val spannableString = SpannableString(text + clickableText)
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                startActivityTo(RegisterActivity::class.java)
            }
        }

        val start = text.length
        val end = (text + clickableText).length
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.color_primary
                )
            ), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvRegister.highlightColor = Color.TRANSPARENT
        binding.tvRegister.text = spannableString
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            if (it.userToken.isNotEmpty()) {
                startActivityTo(MainActivity::class.java)
                finish()
            }
        }

        viewModel.loginGoogleResult.observe(this) {
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
                    Toast.makeText(
                        this,
                        "Login Success with login info name ${it.data?.data?.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivityTo(MainActivity::class.java)
                    finish()
                }
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
                    Toast.makeText(
                        this,
                        "Login Success with login info name ${it.data?.data?.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivityTo(MainActivity::class.java)
                    finish()
                }
            }
        }
    }
}