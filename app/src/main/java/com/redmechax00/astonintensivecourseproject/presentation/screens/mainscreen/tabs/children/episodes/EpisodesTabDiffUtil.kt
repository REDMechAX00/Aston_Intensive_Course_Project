package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes

import androidx.recyclerview.widget.DiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model.EpisodeUIModel

object EpisodesTabDiffUtil : DiffUtil.ItemCallback<EpisodeUIModel>() {

    override fun areItemsTheSame(oldItem: EpisodeUIModel, newItem: EpisodeUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeUIModel, newItem: EpisodeUIModel): Boolean {
        return oldItem == newItem
    }
}