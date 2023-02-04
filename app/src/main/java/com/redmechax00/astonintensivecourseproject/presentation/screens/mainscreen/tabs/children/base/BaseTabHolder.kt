package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseTabHolder<VM: BaseTabItemModel>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var item: VM? = null

    fun bind(bindedItem: VM?) {
        item = bindedItem
        onBind(bindedItem)
    }

    abstract fun onBind(item: VM?)

    fun setItemClickListener(itemIsClicked: (item: VM) -> Unit) {
        itemView.setOnClickListener {
            item?.let { item -> itemIsClicked(item) }
        }
    }

    fun removeItemClickListener() {
        itemView.setOnClickListener(null)
    }
}