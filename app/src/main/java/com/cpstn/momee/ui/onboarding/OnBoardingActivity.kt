package com.cpstn.momee.ui.onboarding

import androidx.viewpager2.widget.ViewPager2
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivityOnBoardingBinding
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    override fun getViewBinding(): ActivityOnBoardingBinding =
        ActivityOnBoardingBinding.inflate(layoutInflater)

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
                    startActivityTo(LoginActivity::class.java)
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

    override fun onPause() {
        super.onPause()

        binding.viewPager.unregisterOnPageChangeCallback(viewPagerCallBack)
    }
}