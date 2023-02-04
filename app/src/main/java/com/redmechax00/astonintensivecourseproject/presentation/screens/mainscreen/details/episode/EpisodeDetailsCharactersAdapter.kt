package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.episode

import android.view.View
import coil.load
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemBaseDetailsCharacterPreviewBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsChildHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.models.BaseDetailsCharacterPreviewUIModel
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base.BaseDetailsCharacterPreviewDiffUtil

class EpisodeDetailsCharactersAdapter(
    private val itemIsClicked: (
        item: BaseDetailsCharacterPreviewUIModel
    ) -> Unit
) : BaseDetailsChildAdapter<
        ItemBaseDetailsCharacterPreviewBinding,
        BaseDetailsCharacterPreviewUIModel,
        EpisodeDetailsCharactersAdapter.EpisodeDetailsCharactersHolder
        >(
    R.layout.item_base_details_character_preview,
    BaseDetailsCharacterPreviewDiffUtil
) {

    override fun createBinding(view: View): ItemBaseDetailsCharacterPreviewBinding =
        ItemBaseDetailsCharacterPreviewBinding.bind(view)

    override fun createViewHolder(
        binding: ItemBaseDetailsCharacterPreviewBinding
    ): EpisodeDetailsCharactersHolder = EpisodeDetailsCharactersHolder(binding)

    override fun onItemClicked(item: BaseDetailsCharacterPreviewUIModel) {
        itemIsClicked(item)
    }

    class EpisodeDetailsCharactersHolder(private val binding: ItemBaseDetailsCharacterPreviewBinding) :
        BaseDetailsChildHolder<BaseDetailsCharacterPreviewUIModel>(binding) {

        override fun onBind(item: BaseDetailsCharacterPreviewUIModel?) {
            with(binding) {
                itemBaseDetailsCharacterPreviewImage.load(item?.image)
                itemBaseDetailsCharacterPreviewName.text = item?.name
            }
        }
    }
}