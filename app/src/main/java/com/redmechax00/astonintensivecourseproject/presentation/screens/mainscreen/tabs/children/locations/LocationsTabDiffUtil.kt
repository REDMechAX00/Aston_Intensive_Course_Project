package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations

import androidx.recyclerview.widget.DiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model.LocationUIModel

object LocationsTabDiffUtil : DiffUtil.ItemCallback<LocationUIModel>() {

    override fun areItemsTheSame(oldItem: LocationUIModel, newItem: LocationUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationUIModel, newItem: LocationUIModel): Boolean {
        return oldItem == newItem
    }
}