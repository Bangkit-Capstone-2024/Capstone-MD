package com.cpstn.momee.ui.homepage

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cpstn.momee.MainViewModel
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.databinding.FragmentHomeBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.bottomsheet.UploadBottomSheet
import com.cpstn.momee.ui.product.SearchProductActivity
import com.cpstn.momee.ui.upload.UploadActivity
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.startActivityTo
import com.cpstn.momee.utils.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    private val BEST_PRODUCT_ID = R.id.frame_best_rating_product
    private val CATEGORY_PRODUCT = R.id.frame_category_product

    override fun setupView() {

        binding.tvGreeting.text = getString(R.string.app_home_greeting, mainViewModel.currentUserInfo?.userName?.split(" ")?.first())

        setupObserver()

        binding.containerSearch.setOnClickListener {
            startActivityTo(SearchProductActivity::class.java)
        }

        binding.containerScan.setOnClickListener {
            val bottomSheet = UploadBottomSheet()
            bottomSheet.setListener(object : UploadBottomSheet.Listener {
                override fun onGalleryClick() {
                    startActivityTo(UploadActivity::class.java)
                    bottomSheet.dismiss()
                }

                override fun onCameraClick() {

                }

            })
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

    }

    private fun setupObserver() {
        viewModel.getCategory()
        viewModel.getAllProduct()
        viewModel.categoryResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> {
                    setupCategoryProductFragment(it.data ?: ProductCategoryDomain())
                }

                is DataResult.Error -> {}
                is DataResult.Loading -> {}
            }
        }
        viewModel.getAllProductResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> {
                    setupBestRatingProductFragment(it.data ?: ProductDomain())
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

    private fun setupBestRatingProductFragment(data: ProductDomain) {
        var fragment = getFragment<BestRatingProductFragment>(BEST_PRODUCT_ID)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to ArrayList(data.data)
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