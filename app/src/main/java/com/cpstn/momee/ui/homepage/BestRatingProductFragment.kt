package com.cpstn.momee.ui.homepage

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cpstn.momee.R
import com.cpstn.momee.databinding.FragmentHomeProductBinding
import com.cpstn.momee.ui.bookmark.adapter.DummyDataClass
import com.cpstn.momee.ui.homepage.adapter.NearestProductAdapter
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.MarginItemDecoration
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.parcelableArrayList
import com.cpstn.momee.utils.percentOf
import com.cpstn.momee.utils.visible

class BestRatingProductFragment : BaseFragment<FragmentHomeProductBinding>(FragmentHomeProductBinding::inflate) {

    private var list: ArrayList<DummyDataClass> = arrayListOf()

    override fun setBundleData() {
        super.setBundleData()

        checkRequireArguments(EXTRAS.DATA) {
            list = requireArguments().parcelableArrayList(it) ?: arrayListOf()
        }
    }

    override fun setupView() {
        binding.apply {
            tvTitle.text = getString(R.string.app_best_rating_in)
            tvDescription.visible(false)
        }
        setupData(list)
    }

    private fun setupData(list: ArrayList<DummyDataClass>) {
        binding.apply {
            rvProducts.adapter = NearestProductAdapter(list)
            rvProducts.layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = 85 percentOf width
                    return true
                }
            }
            if (rvProducts.itemDecorationCount <= 0) {
                rvProducts.addItemDecoration(MarginItemDecoration(16))
            }
        }
    }
}