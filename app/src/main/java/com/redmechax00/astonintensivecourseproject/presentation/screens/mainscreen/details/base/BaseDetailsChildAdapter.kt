package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseDetailsChildAdapter<VB : ViewBinding, VM : BaseDetailsChildItemModel, VH : BaseDetailsChildHolder<VM>>(
    @LayoutRes private val layoutRes: Int,
    diffUtil: DiffUtil.ItemCallback<VM>
) : ListAdapter<VM, VH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutRes, parent, false)
        val binding = createBinding(view)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.setItemClickListener { item ->
            onItemClicked(item)
        }
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.removeItemClickListener()
    }

    abstract fun createBinding(view: View): VB

    abstract fun createViewHolder(binding: VB): VH

    abstract fun onItemClicked(item: VM)
}