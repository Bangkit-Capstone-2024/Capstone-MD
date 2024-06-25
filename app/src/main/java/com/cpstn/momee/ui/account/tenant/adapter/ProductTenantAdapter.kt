package com.cpstn.momee.ui.account.tenant.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.TenantDetailDomain
import com.cpstn.momee.databinding.ItemProductTenantBinding
import com.cpstn.momee.utils.StringHelper


class ProductTenantAdapter(
    private val items: ArrayList<TenantDetailDomain.Data.Product>
) :
    RecyclerView.Adapter<ProductTenantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductTenantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemProductTenantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TenantDetailDomain.Data.Product) = with(binding) {
            ivProduct.load(item.pictures) {
                placeholder(ContextCompat.getDrawable(root.context, R.drawable.ic_placeholder))
            }
            tvName.text = item.nameProducts
            val builder = SpannableStringBuilder()
            val price = StringHelper.convertToCurrencyFormat(item.price.toString(), true)
            builder.bold { append(price) }.append(" / Hari")
            tvPrice.text = builder

            root.setOnClickListener {
//                onItemClick.invoke(item)
                Toast.makeText(root.context, "Eitss, nanti dulu masih belum bisa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}