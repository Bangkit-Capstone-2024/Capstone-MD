package com.cpstn.momee.ui.result

import android.net.Uri
import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.databinding.ActivityResultBinding
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.parcelableArrayList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : BaseActivity<ActivityResultBinding>() {

    private var imageUri: Uri? = null
    private var result: ArrayList<ProductsSearchByImageDomain.Data>? = arrayListOf()

    override fun getViewBinding(): ActivityResultBinding = ActivityResultBinding.inflate(layoutInflater)

    override fun setupView() {

        setupExtra()
        setUpActionBar()

        binding.ivResult.setImageURI(imageUri)
        binding.tvResult.text =
            if (result?.isNotEmpty() == true)
                "Hasil : ${result?.first()?.name.orEmpty()} dengan deskripsi ${result?.first()?.description.orEmpty()}"
            else
                "Data Produk Kosong"
    }

    private fun setupExtra() {
        val imageUriString = Uri.parse(intent?.extras?.getString(EXTRA_IMAGE_URI).orEmpty())
        imageUri = imageUriString
        result = intent?.parcelableArrayList(EXTRA_RESULT)
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