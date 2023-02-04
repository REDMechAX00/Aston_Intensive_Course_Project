package com.redmechax00.astonintensivecourseproject.presentation.customviews.tripletablayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.redmechax00.astonintensivecourseproject.databinding.ViewTripleTabLayoutBinding
import com.redmechax00.astonintensivecourseproject.presentation.customviews.tripletablayout.items.TabItemView

class TripleTabLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IConnectableToViewPager {

    private var binding: ViewTripleTabLayoutBinding
    private var viewPager: ViewPager2? = null
    private val listOfTabItems: List<TabItemView>

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewTripleTabLayoutBinding.inflate(inflater, this)
        listOfTabItems = listOf(
            binding.tripleTabViewFirstItem,
            binding.tripleTabViewSecondItem,
            binding.tripleTabViewThirdItem
        )
        listOfTabItems.forEach { tab -> tab.setOnClickListener { onTabClick(tab) } }
    }

    override fun setViewPager(newViewPager: ViewPager2?) {
        viewPager?.unregisterOnPageChangeCallback(pageChangeCallback)
        newViewPager?.registerOnPageChangeCallback(pageChangeCallback)
        viewPager = newViewPager
    }

    private val pageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            selectTab(position)
        }
    }

    private fun onTabClick(tab: TabItemView) {
        val position = tab.tag.toString().toInt()
        if (position != getSelectedTabPosition()) {
            selectTab(position)
            viewPager?.setCurrentItem(position, true)
        }
    }

    private fun selectTab(position: Int) {
        listOfTabItems.forEach { tab -> tab.isItemSelected = false }
        listOfTabItems[position].isItemSelected = true
    }

    private fun getSelectedTabPosition(): Int {
        listOfTabItems.forEachIndexed { i, tab ->
            if (tab.isItemSelected) return i
        }
        return 0
    }
}