package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base

import androidx.recyclerview.widget.DiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsEpisodePreviewUIModel

object BaseDetailsEpisodePreviewDiffUtil :
    DiffUtil.ItemCallback<BaseDetailsEpisodePreviewUIModel>() {

    override fun areItemsTheSame(
        oldItem: BaseDetailsEpisodePreviewUIModel,
        newItem: BaseDetailsEpisodePreviewUIModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: BaseDetailsEpisodePreviewUIModel,
        newItem: BaseDetailsEpisodePreviewUIModel
    ): Boolean {
        return oldItem == newItem
    }
}