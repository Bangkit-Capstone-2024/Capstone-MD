package com.cpstn.momee.ui.homepage

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cpstn.momee.MainViewModel
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.databinding.FragmentHomeBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.bookmark.adapter.DummyDataClass
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    private val NEAREST_PRODUCT_ID = R.id.frame_nearest_product
    private val BEST_PRODUCT_ID = R.id.frame_best_rating_product
    private val CATEGORY_PRODUCT = R.id.frame_category_product

    override fun setupView() {

        binding.tvGreeting.text = getString(R.string.app_home_greeting, mainViewModel.currentUserInfo?.userName?.split(" ")?.first())
        binding.etSearch.setHint("Cari Barang")
        setupNearestProductFragment()
        setupBestRatingProductFragment()
        setupObserver()

    }

    private fun setupObserver() {
        viewModel.getCategory()
        viewModel.categoryResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> {
                    setupCategoryProductFragment(it.data ?: ProductCategoryDomain())
                }

                is DataResult.Error -> {}
                is DataResult.Loading -> {}
            }
        }

        binding.shimmerLayout.visible(true)
        binding.shimmerLayout.startShimmer()
        mainViewModel.getCurrentLocation.observe(viewLifecycleOwner) {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visible(false)
            binding.tvLocationLabel.visible(it.city.isNotEmpty() && it.province.isNotEmpty())
            binding.tvLocationDesc.visible(it.city.isNotEmpty() && it.province.isNotEmpty())
            binding.tvLocationDesc.text =
                getString(
                    R.string.app_location_label,
                    it.city,
                    it.province
                )
        }
    }

    private fun setupNearestProductFragment() {
        var fragment = getFragment<NearestProductFragment>(NEAREST_PRODUCT_ID)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to DummyDataClass().getDummyList()
            )
            fragment =
                newInstanceFragment(bundle, NearestProductFragment()) as NearestProductFragment
            addChildFragment(NEAREST_PRODUCT_ID, fragment)
        }
    }

    private fun setupBestRatingProductFragment() {
        var fragment = getFragment<BestRatingProductFragment>(BEST_PRODUCT_ID)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to DummyDataClass().getDummyList()
            )
            fragment = newInstanceFragment(
                bundle, BestRatingProductFragment()
            ) as BestRatingProductFragment
            addChildFragment(BEST_PRODUCT_ID, fragment)
        }
    }

    private fun setupCategoryProductFragment(data: ProductCategoryDomain) {
        var fragment = getFragment<CategoryProductFragment>(CATEGORY_PRODUCT)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to ArrayList(data.data)
            )
            fragment =
                newInstanceFragment(bundle, CategoryProductFragment()) as CategoryProductFragment
            addChildFragment(CATEGORY_PRODUCT, fragment)
        }
    }

}