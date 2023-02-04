package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.character

import android.view.View
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemBaseDetailsEpisodePreviewBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsEpisodePreviewDiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsEpisodePreviewUIModel

class CharacterDetailsEpisodesAdapter(
    private val itemIsClicked: (
        item: BaseDetailsEpisodePreviewUIModel
    ) -> Unit
) : BaseDetailsChildAdapter<
        ItemBaseDetailsEpisodePreviewBinding,
        BaseDetailsEpisodePreviewUIModel,
        CharacterDetailsEpisodesAdapter.CharacterDetailsEpisodeHolder
        >(
    R.layout.item_base_details_episode_preview,
    BaseDetailsEpisodePreviewDiffUtil,
) {

    override fun createBinding(view: View): ItemBaseDetailsEpisodePreviewBinding =
        ItemBaseDetailsEpisodePreviewBinding.bind(view)

    override fun createViewHolder(
        binding: ItemBaseDetailsEpisodePreviewBinding
    ): CharacterDetailsEpisodeHolder = CharacterDetailsEpisodeHolder(binding)

    override fun onItemClicked(item: BaseDetailsEpisodePreviewUIModel) {
        itemIsClicked(item)
    }

    class CharacterDetailsEpisodeHolder(private val binding: ItemBaseDetailsEpisodePreviewBinding) :
        BaseDetailsChildHolder<BaseDetailsEpisodePreviewUIModel>(binding) {

        override fun onBind(item: BaseDetailsEpisodePreviewUIModel?) {
            with(binding) {
                itemBaseDetailsEpisodePreviewName.text = item?.name
                itemBaseDetailsEpisodePreviewEpisode.text = item?.episode
            }
        }
    }
}