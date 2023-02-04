package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters

import android.view.View
import coil.load
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ItemCharacterBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabAdapter
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.BaseTabHolder
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model.CharacterUIModel

class CharactersTabAdapter(private val itemIsClicked: (item: CharacterUIModel) -> Unit) :
    BaseTabAdapter<ItemCharacterBinding, CharacterUIModel, CharactersTabAdapter.CharacterTabHolder>(
        R.layout.item_character,
        CharactersTabDiffUtil,
    ) {

    override fun createBinding(view: View): ItemCharacterBinding = ItemCharacterBinding.bind(view)

    override fun createViewHolder(binding: ItemCharacterBinding): CharacterTabHolder =
        CharacterTabHolder(binding)

    override fun onItemClicked(item: CharacterUIModel) {
        itemIsClicked(item)
    }

    class CharacterTabHolder(private val binding: ItemCharacterBinding) :
        BaseTabHolder<CharacterUIModel>(binding) {

        override fun onBind(item: CharacterUIModel?) {
            with(binding) {
                itemCharacterImage.load(item?.image)
                itemCharacterName.text = item?.name
                itemCharacterSpecies.text = item?.species
                itemCharacterStatus.text = item?.status
                itemCharacterGender.text = item?.gender
            }
        }
    }
}