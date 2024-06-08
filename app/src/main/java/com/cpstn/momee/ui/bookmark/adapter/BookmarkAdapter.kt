package com.cpstn.momee.ui.bookmark.adapter

import android.os.Parcelable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.databinding.ItemProductBinding
import com.cpstn.momee.utils.StringHelper
import kotlinx.parcelize.Parcelize


@Parcelize
data class DummyDataClass(
    var review: Int = 0,
    var rating: Int = 0,
    var name: String = "",
    var location: String = "",
    var price: Int = 0,
    var image: String = ""
) : Parcelable {
    fun getDummyList(): ArrayList<DummyDataClass> {
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
        return list
    }
}

@Parcelize
data class ProductCategoryDummy(
    var image: String ="",
    var name: String = "",
    var amount: Int = 0
) : Parcelable {
    fun getDummyList(): ArrayList<ProductCategoryDummy> {
        val list = ArrayList<ProductCategoryDummy>()
        repeat(10) {
            list.add(
                ProductCategoryDummy(
                    image = "https://picsum.photos/id/${(20..40).random()}/200/300",
                    name = "Barang Dummy Random",
                    amount = (100..200).random()
                )
            )
        }
        return list
    }
}

class BookmarkAdapter(private val items: ArrayList<DummyDataClass>) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DummyDataClass) = with(binding) {
            ivProduct.load(item.image)
            val ratingBuilder = SpannableStringBuilder()
                .bold { append(item.rating.toString()) }
                .append(" (${item.review})")
            tvRating.text = ratingBuilder
            tvName.text = item.name
            tvLocation.text = item.location
            val builder = SpannableStringBuilder()
            val price = StringHelper.convertToCurrencyFormat(item.price.toString(), true)
            builder.bold { append(price) }.append(" / bulan")
            tvPrice.text = builder
        }
    }
}