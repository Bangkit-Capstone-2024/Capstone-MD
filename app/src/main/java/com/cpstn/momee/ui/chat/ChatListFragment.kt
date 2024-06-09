package com.cpstn.momee.ui.chat

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.databinding.FragmentChatListBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.chat.adapter.ChatListAdapter
import com.cpstn.momee.ui.chat.adapter.ItemListener
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {

    private val viewModel: ChatListViewModel by viewModels()

    private var mAdapter: ChatListAdapter? = null

    override fun setupView() {

        viewModel.getUserList()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userListResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Loading -> {
                    binding.pbLoading.isVisible = true
                }

                is DataResult.Error -> {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(requireContext(), "Login Error", Toast.LENGTH_SHORT).show()
                }

                is DataResult.Success -> {
                    binding.pbLoading.isVisible = false
                    setupData(it.data ?: UserListDomain())
                }
            }
        }
    }

    private fun setupData(data: UserListDomain) = with(binding) {
        mAdapter = ChatListAdapter(ArrayList(data.query))
        mAdapter?.setupListener(object : ItemListener {
            override fun onItemClick(position: Int) {
                val item = mAdapter?.getItemPosition(position)
                val bundle = bundleOf(
                    EXTRAS.DATA to item
                )
                startActivityTo(ChatActivity::class.java, bundle)
            }

        })
        rvUser.adapter = mAdapter
    }

}