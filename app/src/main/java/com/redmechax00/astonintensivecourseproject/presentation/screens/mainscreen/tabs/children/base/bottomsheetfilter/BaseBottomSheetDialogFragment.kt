package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.tabs.children.base.bottomsheetfilter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.redmechax00.astonintensivecourseproject.R

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding, FM : BaseBottomSheetFilterModel>(
    @LayoutRes private val layoutRes: Int
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding
        get() = requireNotNull(_binding) {
            "Binding isn't init"
        }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutRes, container, false)
        _binding = createBinding(view)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun createBinding(view: View): VB

    abstract fun updateFilterModel(updatedFilterModel: FM)

    fun popupMenuWithItemCallback(
        anchor: View, menuRes: Int,
        onItemClick: (menuItem: MenuItem) -> Unit
    ) {
        val wrapper: Context = ContextThemeWrapper(requireContext(), R.style.PopupMenuItem)
        val popup = PopupMenu(wrapper, anchor)
        popup.inflate(menuRes)
        popup.setOnMenuItemClickListener { menuItem ->
            onItemClick(menuItem)
            true
        }
        popup.show()
    }
}
