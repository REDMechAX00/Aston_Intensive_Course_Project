package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.location

import android.view.View
import coil.load
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemBaseDetailsCharacterPreviewBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsCharacterPreviewDiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel

class LocationDetailsResidentsAdapter(
    private val itemIsClicked: (
        item: BaseDetailsCharacterPreviewUIModel
    ) -> Unit
) : BaseDetailsChildAdapter<
        ItemBaseDetailsCharacterPreviewBinding,
        BaseDetailsCharacterPreviewUIModel,
        LocationDetailsResidentsAdapter.LocationDetailsResidentHolder
        >(
    R.layout.item_base_details_character_preview,
    BaseDetailsCharacterPreviewDiffUtil,
) {

    override fun createBinding(view: View): ItemBaseDetailsCharacterPreviewBinding =
        ItemBaseDetailsCharacterPreviewBinding.bind(view)

    override fun createViewHolder(
        binding: ItemBaseDetailsCharacterPreviewBinding
    ): LocationDetailsResidentHolder = LocationDetailsResidentHolder(binding)

    override fun onItemClicked(item: BaseDetailsCharacterPreviewUIModel) {
        itemIsClicked(item)
    }

    class LocationDetailsResidentHolder(private val binding: ItemBaseDetailsCharacterPreviewBinding) :
        BaseDetailsChildHolder<BaseDetailsCharacterPreviewUIModel>(binding) {

        override fun onBind(item: BaseDetailsCharacterPreviewUIModel?) {
            with(binding) {
                itemBaseDetailsCharacterPreviewImage.load(item?.image)
                itemBaseDetailsCharacterPreviewName.text = item?.name
            }
        }
    }
}