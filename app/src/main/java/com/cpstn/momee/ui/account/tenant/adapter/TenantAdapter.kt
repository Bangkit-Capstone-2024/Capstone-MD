package com.cpstn.momee.ui.account.tenant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.databinding.ItemTenantBinding

class TenantAdapter(
    private val items: ArrayList<TenantDomain.Data>,
    private val onItemClick: (TenantDomain.Data) -> Unit
) :
    RecyclerView.Adapter<TenantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTenantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ViewHolder(private val binding: ItemTenantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TenantDomain.Data) = with(binding) {
            ivTenant.load(item.image)
            tvName.text = item.nameTenants

            root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}