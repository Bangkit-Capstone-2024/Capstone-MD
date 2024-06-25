package com.cpstn.momee.ui.product

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.ProductDetailDomain
import com.cpstn.momee.databinding.ActivityProductDetailBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {

    private val viewModel: ProductViewModel by viewModels()
    private var productId: Int = Constant.ZERO

    override fun getViewBinding(): ActivityProductDetailBinding = ActivityProductDetailBinding.inflate(layoutInflater)

    override fun setupView() {

        setBundleData()
        setupObserver()
        setupActionBar()
    }

    private fun setBundleData() {
        productId = intent.extras?.getInt(EXTRAS.DATA) ?: 0
    }

    private fun setupObserver() {
        viewModel.getDetailProduct(productId)
        viewModel.getDetailProduct.observe(this) {
            when (it) {
                is DataResult.Success -> {
                    handleSuccess(it.data)
                }

                is DataResult.Loading -> {

                }

                is DataResult.Error -> {

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun handleSuccess(data: ProductDetailDomain?) {
        with(binding) {
            data?.let {
                toolbar.title = it.data.nameProducts.orEmpty()
                ivProduct.load(it.data.pictures.orEmpty()) {
                    error(R.drawable.ic_placeholder)
                }
                tvTenant.text = it.data.tenant?.nameTenants.orEmpty()
                tvProductName.text = it.data.nameProducts.orEmpty()
                tvAddress.text = it.data.addressTenants.orEmpty()
                tvDescription.text = it.data.description.orEmpty()
                ivUser.load(it.data.tenant?.image.orEmpty()) {
                    error(R.drawable.ic_profile_active)
                }
                ivChat.setOnClickListener { _ ->
                    val url = "https://api.whatsapp.com/send?phone=${it.data.tenant?.phone.orEmpty()}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(url))
                    startActivity(i)
                }
            }
        }
    }
}