package com.cpstn.momee.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(
    private val image: ArrayList<Int>,
    private val title: ArrayList<String>,
    private val description: ArrayList<String>,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = image.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        val fragment = OnboardingFragment.newInstance(image[position], title[position], description[position])
        return fragment
    }
}