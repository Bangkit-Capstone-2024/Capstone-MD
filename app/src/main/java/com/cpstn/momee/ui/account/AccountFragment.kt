package com.cpstn.momee.ui.account

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cpstn.momee.MainViewModel
import com.cpstn.momee.databinding.FragmentProfileBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.ui.account.tenant.TenantActivity
import com.cpstn.momee.ui.login.LoginActivity
import com.cpstn.momee.utils.base.BaseFragment
import com.cpstn.momee.utils.startActivityTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: AccountViewModel by viewModels()

    override fun setupView() {

        with(binding) {
            tvUser.text = mainViewModel.currentUserInfo?.userName.orEmpty()
            tvEmail.text = mainViewModel.currentUserInfo?.userEmail.orEmpty()

            containerPersonalData.setOnClickListener {

            }
            containerTenant.setOnClickListener {
                startActivityTo(TenantActivity::class.java)
            }
            containerLogout.setOnClickListener {
                viewModel.logout()
            }
        }

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.logoutResult.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> {
                    startActivityTo(LoginActivity::class.java, isClearAllTask = true)
                    requireActivity().finish()
                }

                is DataResult.Error -> { }
                is DataResult.Loading -> { }
            }
        }
    }

}