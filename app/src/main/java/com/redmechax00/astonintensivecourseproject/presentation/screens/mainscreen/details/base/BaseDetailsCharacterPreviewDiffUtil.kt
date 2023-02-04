package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base

import androidx.recyclerview.widget.DiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel

object BaseDetailsCharacterPreviewDiffUtil :
    DiffUtil.ItemCallback<BaseDetailsCharacterPreviewUIModel>() {

    override fun areItemsTheSame(
        oldItem: BaseDetailsCharacterPreviewUIModel,
        newItem: BaseDetailsCharacterPreviewUIModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: BaseDetailsCharacterPreviewUIModel,
        newItem: BaseDetailsCharacterPreviewUIModel
    ): Boolean {
        return oldItem == newItem
    }
}