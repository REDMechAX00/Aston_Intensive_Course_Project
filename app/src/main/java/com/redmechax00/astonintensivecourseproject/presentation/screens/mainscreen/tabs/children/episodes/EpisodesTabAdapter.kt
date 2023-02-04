package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes

import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemEpisodeBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.episodes.model.EpisodeUIModel

class EpisodesTabAdapter(private val itemIsClicked: (item: EpisodeUIModel) -> Unit) :
    BaseTabAdapter<ItemEpisodeBinding, EpisodeUIModel, EpisodesTabAdapter.EpisodeTabHolder>(
        R.layout.item_episode,
        EpisodesTabDiffUtil,
    ) {

    override fun createBinding(view: View): ItemEpisodeBinding = ItemEpisodeBinding.bind(view)

    override fun createViewHolder(binding: ItemEpisodeBinding): EpisodeTabHolder =
        EpisodeTabHolder(binding)

    override fun onItemClicked(item: EpisodeUIModel) {
        itemIsClicked(item)
    }

    class EpisodeTabHolder(private val binding: ItemEpisodeBinding) :
        BaseTabHolder<EpisodeUIModel>(binding) {

        override fun onBind(item: EpisodeUIModel?) {
            with(binding) {
                itemEpisodeName.text = item?.name
                itemEpisodeNumber.text = item?.episode
                itemEpisodeReleaseDate.text = item?.airDate
            }
        }
    }
}