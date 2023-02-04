package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations

import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemLocationBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.locations.model.LocationUIModel

class LocationsTabAdapter(private val itemIsClicked: (item: LocationUIModel) -> Unit) :
    BaseTabAdapter<ItemLocationBinding, LocationUIModel, LocationsTabAdapter.LocationTabHolder>(
        R.layout.item_location,
        LocationsTabDiffUtil,
    ) {

    override fun createBinding(view: View): ItemLocationBinding = ItemLocationBinding.bind(view)

    override fun createViewHolder(binding: ItemLocationBinding): LocationTabHolder =
        LocationTabHolder(binding)

    override fun onItemClicked(item: LocationUIModel) {
        itemIsClicked(item)
    }

    class LocationTabHolder(private val binding: ItemLocationBinding) :
        BaseTabHolder<LocationUIModel>(binding) {

        override fun onBind(item: LocationUIModel?) {
            with(binding) {
                itemLocationName.text = item?.name
                itemLocationType.text = item?.type
                itemLocationDimension.text = item?.dimension
            }
        }
    }
}