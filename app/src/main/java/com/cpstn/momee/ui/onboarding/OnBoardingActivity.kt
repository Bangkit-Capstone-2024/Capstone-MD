package com.cpstn.momee.ui.onboarding

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivityOnBoardingBinding
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    private val viewModel: OnboardingViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.saveUserSession()
            goToLogin()
        } else {
            Snackbar.make(
                binding.root,
                "Aplikasi membutuhkan izin notifikasi",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Pergi ke pengaturan") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    startActivity(settingsIntent)
                }
            }.show()
        }
    }

    override fun getViewBinding(): ActivityOnBoardingBinding = ActivityOnBoardingBinding.inflate(layoutInflater)

    val listImage = arrayListOf(R.drawable.il_boy, R.drawable.il_girl, R.drawable.il_father)
    val listTitle = arrayListOf(
        "Selamat datang di Momee!",
        "Aman dan Terpercaya",
        "Fleksibel dan Praktis"
    )
    val listDescription = arrayListOf(
        "Nikmati kemudahan merental peralatan bayi dan anak berkualitas tanpa repot",
        "Setiap produk di Momee telah melalui proses sanitasi dan pemeriksaan kualitas",
        "Hemat ruang, hemat biaya, dan dapatkan pengalaman yang lebih praktis!"
    )

    private val viewPagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.btnNext.text = if (position == listImage.size - 1) "Mulai" else "Lanjut"
            binding.btnNext.setOnClickListener {
                if (position != listImage.size - 1)
                    binding.viewPager.currentItem = position + 1
                else
                    askNotificationPermission()
            }
        }
    }

    override fun setupView() {
        with(binding) {

            viewPager.adapter = OnBoardingAdapter(listImage, listTitle, listDescription, this@OnBoardingActivity)
            viewIndicator.setupWithViewPager(viewPager)
            viewPager.registerOnPageChangeCallback(viewPagerCallBack)

            btnSkip.setOnClickListener {
                startActivityTo(LoginActivity::class.java)
                finish()
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.saveUserSession()
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivityTo(LoginActivity::class.java)
        finish()
    }

    override fun onPause() {
        super.onPause()

        binding.viewPager.unregisterOnPageChangeCallback(viewPagerCallBack)
    }
}