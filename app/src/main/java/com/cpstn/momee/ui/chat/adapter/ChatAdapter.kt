package com.cpstn.momee.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpstn.momee.databinding.ItemChatReceiveBinding
import com.cpstn.momee.databinding.ItemChatSendBinding
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.StringHelper
import com.cpstn.momee.utils.visible

data class Message(
    var message: String = "",
    var senderId: String = "",
    var date: String = Constant.EMPTY_STRING
)

class ChatAdapter(private val items: List<Message>, private val currentUserEmail: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_SENT = 0
        private const val ITEM_RECEIVE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val binding =
                ItemChatSendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatSendViewHolder(binding)
        } else {
            val binding =
                ItemChatReceiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatReceiveViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].senderId == currentUserEmail) ITEM_SENT else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatSendViewHolder -> holder.bind(items[position])
            is ChatReceiveViewHolder -> holder.bind(items[position])
        }
    }

    inner class ChatSendViewHolder(private val binding: ItemChatSendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Message) {
            binding.tvSend.text = item.message
            binding.tvDate.visible(item.date.isNotEmpty())
            val isAvailableDate = item.date.isNotEmpty()
            if (isAvailableDate) {
                binding.tvDate.text = StringHelper.convertDateToString(item.date)
            }
        }

    }

    inner class ChatReceiveViewHolder(private val binding: ItemChatReceiveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Message) {
            binding.tvSend.text = item.message
            binding.tvDate.visible(item.date.isNotEmpty())
            val isAvailableDate = item.date.isNotEmpty()
            if (isAvailableDate) {
                binding.tvDate.text = StringHelper.convertDateToString(item.date)
            }
        }
    }
}

