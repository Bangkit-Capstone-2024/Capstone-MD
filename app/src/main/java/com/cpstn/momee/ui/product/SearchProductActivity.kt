package com.cpstn.momee.ui.product

import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.databinding.ActivitySearchProductBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.product.adapter.SearchProductAdapter
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.GridItemDecoration
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.hideKeyboard
import com.cpstn.momee.utils.startActivityTo
import com.cpstn.momee.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   SearchProductActivity : BaseActivity<ActivitySearchProductBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    override fun getViewBinding(): ActivitySearchProductBinding = ActivitySearchProductBinding.inflate(layoutInflater)

    private var query: String = Constant.EMPTY_STRING

    override fun setupView() {

        setBundleData()
        setupObserver()

        binding.etProductName.setHint("Masukan nama produk")

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setBundleData() {
        query = intent.extras?.getString(EXTRAS.DATA).orEmpty()
        binding.etProductName.setText(query)
        binding.etProductName.doOnTextChanged { text, start, before, count ->
            if ((text?.length ?: 0) <= 0) {
                query = Constant.EMPTY_STRING
                setupObserver()
            }
        }
        binding.etProductName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(this)
                query = binding.etProductName.text.toString()
                setupObserver()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.tilSearch.setEndIconOnClickListener {
            hideKeyboard(this)
            query = binding.etProductName.text.toString()
            setupObserver()
        }
    }

    private fun setupObserver() {
        if (query.isEmpty()) {
            viewModel.getAllProduct()
            viewModel.getAllProductResult.observe(this) {
                when (it) {
                    is DataResult.Loading -> {
                        binding.pbLoading.visible(true)
                        binding.containerEmpty.visible(!binding.pbLoading.isVisible)
                        binding.rvProduct.visible(!binding.pbLoading.isVisible)
                    }

                    is DataResult.Success -> {
                        binding.pbLoading.visible(false)
                        handleSuccess(ArrayList(it.data?.data ?: listOf()))
                    }

                    is DataResult.Error -> {
                        binding.pbLoading.visible(false)
                        binding.containerEmpty.visible(true)
                        binding.rvProduct.visible(!binding.containerEmpty.isVisible)
                        binding.tvState.text = it.error
                    }
                }
            }
        } else {
            viewModel.searchProduct(query)
            viewModel.searchProductResult.observe(this) {
                when (it) {
                    is DataResult.Loading -> {
                        binding.pbLoading.visible(true)
                        binding.containerEmpty.visible(!binding.pbLoading.isVisible)
                        binding.rvProduct.visible(!binding.pbLoading.isVisible)
                    }

                    is DataResult.Success -> {
                        binding.pbLoading.visible(false)
                        handleSuccess(ArrayList(it.data?.data ?: listOf()))
                    }

                    is DataResult.Error -> {
                        binding.pbLoading.visible(false)
                        binding.containerEmpty.visible(true)
                        binding.rvProduct.visible(!binding.containerEmpty.isVisible)
                        binding.tvState.text = it.error
                    }
                }
            }
        }
    }

    private fun handleSuccess(list: ArrayList<ProductDomain.Data>) {
        binding.containerEmpty.visible(list.isEmpty())
        binding.rvProduct.visible(!binding.containerEmpty.isVisible)
        binding.rvProduct.adapter = SearchProductAdapter(ArrayList(list.reversed())) {
            startActivityTo(ProductDetailActivity::class.java, bundleOf(EXTRAS.DATA to it.id))
        }
        if (binding.rvProduct.itemDecorationCount <= 0) {
            binding.rvProduct.addItemDecoration(GridItemDecoration(16, 2))
        }
    }

}