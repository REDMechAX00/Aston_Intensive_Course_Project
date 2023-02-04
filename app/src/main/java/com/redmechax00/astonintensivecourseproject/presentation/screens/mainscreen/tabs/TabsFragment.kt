package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs

import android.os.Bundle
import android.view.View
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentTabsBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.base.BaseFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.CharactersTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.EpisodesTabFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.LocationsTabFragment

class TabsFragment : BaseFragment<FragmentTabsBinding>(
    R.layout.fragment_tabs
) {

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication)
            .getAppComponent()
            .inject(this)
    }

    override fun createBinding(view: View): FragmentTabsBinding =
        FragmentTabsBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    override fun onResume() {
        super.onResume()
        setDefaultStatusBarColor()
    }

    private fun setDefaultStatusBarColor() {
        requireActivity().window.statusBarColor =
            requireContext().getColor(R.color.color_primary_variant)
    }

    override fun onDestroyFragmentView() {
        binding.fragmentTabsViewPager.adapter = null
    }

    private fun setupViewPager() {
        val tabsPagerAdapter = TabsPagerAdapter(
            this,
            listOf(CharactersTabFragment(), LocationsTabFragment(), EpisodesTabFragment())
        )
        binding.fragmentTabsViewPager.adapter = tabsPagerAdapter
        binding.fragmentTabsTripleTabLayout.setViewPager(binding.fragmentTabsViewPager)
    }
}