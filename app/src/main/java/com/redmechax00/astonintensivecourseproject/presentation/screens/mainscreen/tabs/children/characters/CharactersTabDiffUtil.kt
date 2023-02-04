package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters

import androidx.recyclerview.widget.DiffUtil
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.characters.model.CharacterUIModel

object CharactersTabDiffUtil : DiffUtil.ItemCallback<CharacterUIModel>() {

    override fun areItemsTheSame(oldItem: CharacterUIModel, newItem: CharacterUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterUIModel, newItem: CharacterUIModel): Boolean {
        return oldItem == newItem
    }
}