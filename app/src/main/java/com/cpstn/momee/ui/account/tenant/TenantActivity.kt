package com.cpstn.momee.ui.account.tenant

import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.databinding.ActivityTenantBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.account.tenant.adapter.TenantAdapter
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.GridItemDecoration
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.startActivityTo
import com.cpstn.momee.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenantActivity : BaseActivity<ActivityTenantBinding>() {

    private val viewModel: TenantViewModel by viewModels()

    override fun getViewBinding(): ActivityTenantBinding =
        ActivityTenantBinding.inflate(layoutInflater)

    override fun setupView() {

        setupObserver()
        setupActionBar()

        binding.fabAddTenant.setOnClickListener {
            startActivityTo(CreateTenantActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTenant()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObserver() {
        viewModel.tenantResult.observe(this) {
            when (it) {
                is DataResult.Success -> {
                    binding.pbLoading.visible(false)
                    handleSuccess(it.data)
                }

                is DataResult.Error -> {
                    binding.pbLoading.visible(false)
                }

                is DataResult.Loading -> {
                    binding.pbLoading.visible(true)
                }
            }
        }
    }

    private fun handleSuccess(data: TenantDomain?) {
        data?.let {
            binding.containerEmpty.visible(it.data.isEmpty())
            binding.rvTenant.visible(!binding.containerEmpty.isVisible)
            binding.rvTenant.adapter = TenantAdapter(ArrayList(it.data)) { data ->
                startActivityTo(TenantDetailActivity::class.java, bundleOf(EXTRAS.DATA to data))
            }
            if (binding.rvTenant.itemDecorationCount <= 0) {
                binding.rvTenant.addItemDecoration(GridItemDecoration(16, 2))
            }
        }
    }
}