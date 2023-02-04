package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemErrorBinding

class LocationsLoadingStateAdapter(
    private val adapter: LocationsTabAdapter,
    private val onOfflineClick: () -> Unit
) : LoadStateAdapter<LocationsLoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemErrorBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_error, parent, false)
            ),
            onOfflineClick
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ItemErrorBinding,
        private val offlineCallback: () -> Unit,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemErrorBtnOffline.setOnClickListener { offlineCallback() }
            binding.itemErrorBtnRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {

                itemProgress.isVisible = loadState is LoadState.Loading

                if ((loadState as? LoadState.Error)?.error?.message != null &&
                    (loadState as? LoadState.Error)?.error?.message != "HTTP 404 "
                ) {
                    itemErrorContainer.isVisible = true
                } else {
                    itemProgress.isVisible = false
                    itemErrorContainer.isVisible = false
                }
            }
        }
    }
}
