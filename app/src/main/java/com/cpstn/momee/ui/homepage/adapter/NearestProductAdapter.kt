package com.cpstn.momee.ui.homepage.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.databinding.ItemProductBinding
import com.cpstn.momee.utils.StringHelper

class NearestProductAdapter(
    private val items: ArrayList<ProductDomain.Data>,
    private val onItemClick: (ProductDomain.Data) -> Unit
) :
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

        fun bind(item: ProductDomain.Data) = with(binding) {
            ivProduct.load(item.pictures) {
                error(R.drawable.ic_placeholder)
            }
            tvName.text = item.nameProducts
            tvLocation.text = item.addressTenants
            val builder = SpannableStringBuilder()
            val price = StringHelper.convertToCurrencyFormat(item.price.toString(), true)
            builder.bold { append(price) }.append(" / hari")
            tvPrice.text = builder

            root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}