package com.redmechax00.astonintensivecourseproject.presentation.customviews.tripletablayout.items

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ViewTabItemBinding

class TabItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ISelectableTabItem {

    override var isItemSelected: Boolean = false
        set(value) {
            if (value != field || !value) updateUIWithSelected(value)
            field = value
        }

    private var binding: ViewTabItemBinding

    private val selectorBackgroundDrawable: GradientDrawable

    private val iconNormal: Drawable?
    private val iconSelected: Drawable?
    private val colorNormal = ContextCompat.getColor(context, R.color.color_tab_normal)
    private val colorSelected =
        ContextCompat.getColor(context, R.color.color_tab_selected)

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTabItemBinding.inflate(inflater, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TabItemView, 0, 0)
        try {
            iconNormal = attributes.getDrawable(R.styleable.TabItemView_tab_item_icon_normal)
            iconSelected = attributes.getDrawable(R.styleable.TabItemView_tab_item_icon_selected)
            binding.tabItemViewTitle.text =
                attributes.getString(R.styleable.TabItemView_android_text)
        } finally {
            attributes.recycle()
        }

        selectorBackgroundDrawable =
            (binding.tabItemViewSelector.background as LayerDrawable)
                .findDrawableByLayerId(R.id.tab_view_selector_background) as GradientDrawable
        binding.tabItemViewIcon.setImageDrawable(iconNormal)
    }

    private fun updateUIWithSelected(isSelected: Boolean) {
        if (isSelected) {
            selectorBackgroundDrawable.setTintList(ColorStateList.valueOf(colorSelected))
            ObjectAnimator.ofFloat(binding.tabItemViewSelector, "scaleX", 0f, 1f)
                .setDuration(400)
                .start()
            binding.tabItemViewIcon.setImageDrawable(iconSelected)
            binding.tabItemViewTitle.setTypeface(null, Typeface.BOLD)
        } else {
            selectorBackgroundDrawable.setTint(colorNormal)
            binding.tabItemViewIcon.setImageDrawable(iconNormal)
            binding.tabItemViewTitle.setTypeface(null, Typeface.NORMAL)
        }
    }
}