package com.cpstn.momee.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.databinding.ItemChatListBinding

interface ItemListener {
    fun onItemClick(position: Int)
}

class ChatListAdapter(private val items: ArrayList<UserListDomain.Query>) :
    RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private var listener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getItemPosition(position: Int) = items[position]

    fun setupListener(listener: ItemListener) {
        this.listener = listener
    }

    inner class ViewHolder(
        private val binding: ItemChatListBinding,
        private val listener: ItemListener? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserListDomain.Query) = with(binding) {
            ivUser.load(R.drawable.ic_profile_active)
            tvUserName.text = item.username
            root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}