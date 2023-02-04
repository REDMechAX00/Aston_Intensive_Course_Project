package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter

interface BottomSheetFilterCallback<FM> {
    fun onBottomSheetResetClick()
    fun onBottomSheetApplyClick(filter: FM)
}