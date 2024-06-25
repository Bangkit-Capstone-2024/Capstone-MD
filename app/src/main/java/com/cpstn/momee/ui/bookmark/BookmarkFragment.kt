package com.cpstn.momee.ui.bookmark

import com.cpstn.momee.databinding.FragmentBookmarkBinding
import com.cpstn.momee.ui.bookmark.adapter.BookmarkAdapter
import com.cpstn.momee.ui.bookmark.adapter.DummyDataClass
import com.cpstn.momee.utils.base.BaseFragment

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::inflate) {

    override fun setupView() {

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.apply {
            val list = ArrayList<DummyDataClass>()
            repeat(10) {
                list.add(
                    DummyDataClass(
                        image = "https://picsum.photos/id/${(20..40).random()}/200/300",
                        rating = (3..5).random(),
                        review = (50..100).random(),
                        name = "Barang Dummy Random",
                        location = "Cakung, Jakarta",
                        price = 150_000
                    )
                )
            }

            rvProduct.adapter = BookmarkAdapter(list)
        }
    }

}