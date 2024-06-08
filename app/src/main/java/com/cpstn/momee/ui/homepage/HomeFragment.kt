package com.cpstn.momee.ui.homepage

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.cpstn.momee.R
import com.cpstn.momee.databinding.FragmentHomeBinding
import com.cpstn.momee.databinding.FragmentHomeProductBinding
import com.cpstn.momee.ui.bookmark.adapter.DummyDataClass
import com.cpstn.momee.ui.bookmark.adapter.ProductCategoryDummy
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val NEAREST_PRODUCT_ID = R.id.frame_nearest_product
    private val BEST_PRODUCT_ID = R.id.frame_best_rating_product
    private val CATEGORY_PRODUCT = R.id.frame_category_product

    override fun setupView() {

        setupNearestProductFragment()
        setupBestRatingProductFragment()
        setupCategoryProductFragment()
    }


    private fun setupNearestProductFragment() {
        var fragment = getFragment<NearestProductFragment>(NEAREST_PRODUCT_ID)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to DummyDataClass().getDummyList()
            )
            fragment = newInstanceFragment(bundle, NearestProductFragment()) as NearestProductFragment
            addChildFragment(NEAREST_PRODUCT_ID, fragment)
        }
    }

    private fun setupBestRatingProductFragment() {
        var fragment = getFragment<BestRatingProductFragment>(BEST_PRODUCT_ID)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to DummyDataClass().getDummyList()
            )
            fragment = newInstanceFragment(bundle, BestRatingProductFragment()) as BestRatingProductFragment
            addChildFragment(BEST_PRODUCT_ID, fragment)
        }
    }

    private fun setupCategoryProductFragment() {
        var fragment = getFragment<CategoryProductFragment>(CATEGORY_PRODUCT)
        if (fragment == null) {
            val bundle = bundleOf(
                EXTRAS.DATA to ProductCategoryDummy().getDummyList()
            )
            fragment = newInstanceFragment(bundle, CategoryProductFragment()) as CategoryProductFragment
            addChildFragment(CATEGORY_PRODUCT, fragment)
        }
    }

    private fun addFragments(
        ids: ArrayList<Int>,
        fragments: ArrayList<BaseFragment<FragmentHomeProductBinding>>
    ) {
        ids.forEachIndexed { index, id ->
            val fragment = childFragmentManager.findFragmentById(id)
            if (fragment == null) {
                this.replaceChildFragment(id, fragments[index])
            }
        }
    }

    private fun Fragment.replaceChildFragment(containerViewId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Fragment> Fragment.getFragment(fragmentId: Int): T? {
        return childFragmentManager.findFragmentById(fragmentId) as? T
    }

}