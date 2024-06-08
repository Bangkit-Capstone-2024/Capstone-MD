package com.cpstn.momee.ui.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.databinding.ItemProductCategoryBinding
import com.cpstn.momee.ui.bookmark.adapter.ProductCategoryDummy

class ProductCategoryAdapter(private val items: ArrayList<ProductCategoryDummy>) :
    RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductCategoryDummy) = with(binding) {
            ivProduct.load(item.image)
            tvName.text = item.name
            tvLabel.text = "${item.amount} barang"
        }
    }
}