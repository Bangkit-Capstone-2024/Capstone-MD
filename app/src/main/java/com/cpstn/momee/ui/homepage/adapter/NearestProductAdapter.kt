package com.cpstn.momee.ui.homepage.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.databinding.ItemProductBinding
import com.cpstn.momee.ui.bookmark.adapter.DummyDataClass
import com.cpstn.momee.utils.StringHelper

class NearestProductAdapter(private val items: ArrayList<DummyDataClass>) :
    RecyclerView.Adapter<NearestProductAdapter.ViewHolder>() {

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