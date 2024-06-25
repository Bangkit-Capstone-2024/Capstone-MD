package com.cpstn.momee.ui.account.tenant

import android.view.Menu
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.TenantDetailDomain
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.databinding.ActivityTenantDetailBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.account.tenant.adapter.ProductTenantAdapter
import com.cpstn.momee.ui.account.tenant.product.SubmitProductTenantActivity
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.GridItemDecoration
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.parcelable
import com.cpstn.momee.utils.startActivityTo
import com.cpstn.momee.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenantDetailActivity : BaseActivity<ActivityTenantDetailBinding>() {

    private val viewModel: TenantViewModel by viewModels()

    private var tenantData: TenantDomain.Data = TenantDomain.Data()

    override fun getViewBinding(): ActivityTenantDetailBinding = ActivityTenantDetailBinding.inflate(layoutInflater)

    override fun setupView() {

        setBundleData()
        setupActionBar()
        setupObserver()
    }

    private fun setBundleData() {
        tenantData = intent.extras?.parcelable<TenantDomain.Data>(EXTRAS.DATA) ?: TenantDomain.Data()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDetailTenant(tenantData.id ?: 0)
    }

    private fun setupObserver() {
        viewModel.tenantDetailResult.observe(this) {
            when (it) {
                is DataResult.Success -> {
//                    binding.pbLoading.visible(false)
                    handleSuccess(it.data?.data ?: TenantDetailDomain.Data())
                }

                is DataResult.Error -> {
//                    binding.pbLoading.visible(false)
                }

                is DataResult.Loading -> {
//                    binding.pbLoading.visible(true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tenant, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit_tenant -> {
                    startActivityTo(CreateTenantActivity::class.java, bundleOf(EXTRAS.DATA to tenantData))
                }

                R.id.menu_add_product -> {
                    startActivityTo(SubmitProductTenantActivity::class.java, bundleOf(EXTRAS.DATA to tenantData))
                }
            }
            true
        }
    }

    private fun handleSuccess(data: TenantDetailDomain.Data) {
        data.let {
            binding.containerEmpty.visible(data.products.isEmpty())
            binding.rvProduct.visible(!binding.containerEmpty.isVisible)
            binding.containerCollapsingToolbar.title = data.nameTenants
            binding.toolbar.title = data.nameTenants
            binding.ivUser.load(data.image)
            binding.tvTenant.text = data.nameTenants.orEmpty()
            binding.tvAddress.text = data.addressTenants.orEmpty()
            binding.rvProduct.adapter = ProductTenantAdapter(ArrayList(it.products))
            if (binding.rvProduct.itemDecorationCount <= 0) {
                binding.rvProduct.addItemDecoration(GridItemDecoration(16, 2))
            }
        }
    }
}