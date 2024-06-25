package com.cpstn.momee.ui.homepage

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.databinding.FragmentHomeProductBinding
import com.cpstn.momee.ui.homepage.adapter.ProductCategoryAdapter
import com.cpstn.momee.ui.product.SearchProductActivity
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.MarginItemDecoration
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.parcelableArrayList
import com.cpstn.momee.utils.startActivityTo
import com.cpstn.momee.utils.visible

class CategoryProductFragment: BaseFragment<FragmentHomeProductBinding>(FragmentHomeProductBinding::inflate) {

    private var list: ArrayList<ProductCategoryDomain.Data> = arrayListOf()

    override fun setBundleData() {
        super.setBundleData()

        checkRequireArguments(EXTRAS.DATA) {
            list = requireArguments().parcelableArrayList(it) ?: arrayListOf()
        }
    }

    override fun setupView() {
        binding.apply {
            tvTitle.text = "Cari barang sesuai kategori!"
            tvDescription.visible(false)
        }
        setupData(list)
    }

    private fun setupData(list: ArrayList<ProductCategoryDomain.Data>) {
        binding.apply {
            rvProducts.adapter = ProductCategoryAdapter(list) {
                val bundle = bundleOf(EXTRAS.DATA to it.nameCategories.orEmpty() )
                startActivityTo(SearchProductActivity::class.java, bundle)
            }
            rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            if (rvProducts.itemDecorationCount <= 0) {
                rvProducts.addItemDecoration(MarginItemDecoration(16))
            }
        }
    }
}