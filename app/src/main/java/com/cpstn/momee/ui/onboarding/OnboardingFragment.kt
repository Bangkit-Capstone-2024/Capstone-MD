package com.cpstn.momee.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import com.cpstn.momee.databinding.FragmentOnboardingBinding
import com.cpstn.momee.utils.Constant.EMPTY_STRING
import com.cpstn.momee.utils.Constant.ZERO
import com.cpstn.momee.utils.EXTRAS.DESCRIPTION
import com.cpstn.momee.utils.EXTRAS.IMAGE
import com.cpstn.momee.utils.EXTRAS.TITLE
import com.cpstn.momee.utils.base.BaseFragment

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private var image: Int = ZERO
    private var title: String = EMPTY_STRING
    private var description: String = EMPTY_STRING

    companion object {
        fun newInstance(@DrawableRes image: Int, title: String, description: String) =
            OnboardingFragment().apply {
                arguments = bundleOf(
                    IMAGE to image,
                    TITLE to title,
                    DESCRIPTION to description
                )
            }
    }

    override fun setBundleData() {
        super.setBundleData()

        checkRequireArguments(IMAGE) {
            image = requireArguments().getInt(it)
        }

        checkRequireArguments(TITLE) {
            title = requireArguments().getString(it).orEmpty()
        }

        checkRequireArguments(DESCRIPTION) {
            description = requireArguments().getString(it).orEmpty()
        }
    }

    override fun setupView() {
        with(binding) {
            ivOnboarding.setImageResource(image)
            tvTitle.text = title
            tvDescription.text = description
        }
    }

}