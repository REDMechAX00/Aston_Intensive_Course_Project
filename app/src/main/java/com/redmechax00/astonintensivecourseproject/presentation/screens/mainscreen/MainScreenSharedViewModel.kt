package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainScreenSharedViewModel @Inject constructor() : ViewModel() {

    private val networkStatusLiveData = MutableLiveData<ConnectivityObserver.Status>()
    val networkStatusLive: LiveData<ConnectivityObserver.Status> = networkStatusLiveData

    private val lastOpenedFragmentLiveData = MutableLiveData<String>()
    val lastOpenedFragmentLive: LiveData<String> = lastOpenedFragmentLiveData

    var lastOpenedDetailsId: Int? = null

    init {
        networkStatusLiveData.value = ConnectivityObserver.Status.Available
        lastOpenedFragmentLiveData.value = MainScreenFragment.TAG_FRAGMENT_TABS
    }

    fun onUpdateNetworkStatus(status: ConnectivityObserver.Status) {
        networkStatusLiveData.value = status
    }

    fun onStartTabsFragment(fragmentTag: String?) {
        fragmentTag.let { tag -> lastOpenedFragmentLiveData.value = tag }
    }

    fun onStartDetailsFragment(fragmentTag: String?, detailsId: Int) {
        lastOpenedDetailsId = detailsId
        fragmentTag.let { tag -> lastOpenedFragmentLiveData.value = tag }
    }
}