package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.redmechax00.astonintensivecourseproject.MyApplication
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.FragmentMainScreenBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.base.BaseFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character.CharacterDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode.EpisodeDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location.LocationDetailsFragment
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.TabsFragment
import com.redmechax00.astonintensivecourseproject.util.getConnectionType
import com.redmechax00.astonintensivecourseproject.util.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>(
    R.layout.fragment_main_screen
) {

    companion object {
        const val TAG_FRAGMENT_TABS = "fragment_tabs"
        const val TAG_FRAGMENT_CHARACTER_DETAILS = "fragment_character_details"
        const val TAG_FRAGMENT_LOCATION_DETAILS = "fragment_location_details"
        const val TAG_FRAGMENT_EPISODE_DETAILS = "fragment_episode_details"
        const val TAG_EXTRA_DETAILS_ID = "details_id"
        const val NETWORK_STATUS_DELAY_MILLIS = 2000L
    }

    private lateinit var fragManager: FragmentManager
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun initDaggerComponent() {
        (requireActivity().application as MyApplication).getAppComponent().inject(this)
    }

    override fun createBinding(view: View): FragmentMainScreenBinding =
        FragmentMainScreenBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentManager()
        observeNetworkConnection()
        observeData()
    }

    override fun onDestroyFragmentView() {}

    private fun initFragmentManager() {
        fragManager = requireActivity().supportFragmentManager
        fragManager.addOnBackStackChangedListener {
            if (fragManager.backStackEntryCount == 0) {
                mainScreenSharedViewModel.onStartTabsFragment(TAG_FRAGMENT_TABS)
            } else {
                mainScreenSharedViewModel.onStartTabsFragment(fragManager.fragments.last().tag)
            }
        }
    }

    private fun observeNetworkConnection() {
        connectivityObserver = NetworkConnectivityObserver(requireActivity().applicationContext)
        connectivityObserver.observe()
            .onEach { status -> mainScreenSharedViewModel.onUpdateNetworkStatus(status) }
            .launchIn(lifecycleScope)
    }

    private fun observeData() {
        when (getConnectionType(requireContext())) {
            0 -> mainScreenSharedViewModel.onUpdateNetworkStatus(ConnectivityObserver.Status.Unavailable)
            else -> mainScreenSharedViewModel.onUpdateNetworkStatus(ConnectivityObserver.Status.Available)
        }

        mainScreenSharedViewModel.networkStatusLive.observe(viewLifecycleOwner) { connectionStatus ->
            when (connectionStatus) {
                ConnectivityObserver.Status.Available -> applyConnectionRestoredToUI()
                else -> applyLostConnectionToUI()
            }
        }

        mainScreenSharedViewModel.lastOpenedFragmentLive.observe(viewLifecycleOwner) {
            tryStartFragment(it)
        }
    }

    private fun applyConnectionRestoredToUI() {
        with(binding.mainScreenNetworkStatus) {
            setBackgroundColor(requireContext().getColor(R.color.color_network_status_connect))
            text = requireContext().getString(R.string.text_connection_restored)
            delayedGoneIfConnectionIsAvailable()
        }
    }

    private fun applyLostConnectionToUI() {
        with(binding.mainScreenNetworkStatus) {
            setBackgroundColor(requireContext().getColor(R.color.color_network_status_lost))
            text = requireContext().getString(R.string.text_connection_is_lost)
            show()
        }
    }

    private fun View.delayedGoneIfConnectionIsAvailable() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            delay(NETWORK_STATUS_DELAY_MILLIS)
            this@delayedGoneIfConnectionIsAvailable.isVisible =
                mainScreenSharedViewModel.networkStatusLive.value != ConnectivityObserver.Status.Available
        }
    }

    private fun tryStartFragment(fragmentTag: String) {
        prepareFragmentByTag(fragmentTag) { fragment, addToBackStack ->
            if (getLastFragmentTag() != fragmentTag) {
                startFragment(
                    fragment,
                    fragmentTag,
                    mainScreenSharedViewModel.lastOpenedDetailsId,
                    addToBackStack
                )
            }
        }
    }

    private fun prepareFragmentByTag(
        fragmentTag: String,
        onPrepared: (fragment: Fragment, addToBackStack: Boolean) -> Unit
    ) {
        val addToBackStack: Boolean
        val fragment = when (fragmentTag) {
            TAG_FRAGMENT_CHARACTER_DETAILS -> {
                addToBackStack = true
                CharacterDetailsFragment()
            }
            TAG_FRAGMENT_LOCATION_DETAILS -> {
                addToBackStack = true
                LocationDetailsFragment()
            }
            TAG_FRAGMENT_EPISODE_DETAILS -> {
                addToBackStack = true
                EpisodeDetailsFragment()
            }
            else -> {
                addToBackStack = false
                TabsFragment()
            }
        }
        onPrepared(fragment, addToBackStack)
    }

    private fun startFragment(
        fragment: Fragment,
        fragmentTag: String,
        detailsId: Int?,
        addToBackStack: Boolean
    ) {
        val arg = Bundle()
        arg.putInt(TAG_EXTRA_DETAILS_ID, detailsId ?: 0)
        fragment.arguments = arg

        val fragmentTransaction = fragManager.beginTransaction()
            .replace(R.id.main_screen_fragment_container, fragment, fragmentTag)
        if (addToBackStack) fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun getLastFragmentTag(): String? = fragManager.fragments.last().tag
}