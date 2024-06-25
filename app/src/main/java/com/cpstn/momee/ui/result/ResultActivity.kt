package com.cpstn.momee.ui.result

import android.net.Uri
import com.cpstn.momee.databinding.ActivityResultBinding
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : BaseActivity<ActivityResultBinding>() {

    private var imageUri: Uri? = null
    private var result: String = Constant.EMPTY_STRING

    override fun getViewBinding(): ActivityResultBinding = ActivityResultBinding.inflate(layoutInflater)

    override fun setupView() {

        setupExtra()
        setUpActionBar()

        binding.ivResult.setImageURI(imageUri)
        binding.tvResult.text =
            if (result.isNotEmpty())
                "Hasil : $result"
            else
                "Data Produk Kosong"
    }

    private fun setupExtra() {
        val imageUriString = Uri.parse(intent?.extras?.getString(EXTRA_IMAGE_URI).orEmpty())
        imageUri = imageUriString
        result = intent?.extras?.getString(EXTRA_RESULT).orEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val EXTRA_RESULT = "EXTRA_RESULT"
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
    }
}