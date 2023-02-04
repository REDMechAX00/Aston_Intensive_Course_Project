package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    parentFragment: Fragment, private val listOfFragments: List<Fragment>
) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int {
        return listOfFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return listOfFragments[position]
    }
}