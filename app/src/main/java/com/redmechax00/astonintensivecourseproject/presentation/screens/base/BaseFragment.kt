package com.redmechax00.astonintensivecourseproject.presentation.screens.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenSharedViewModel
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    private var _mainScreenSharedViewModel: MainScreenSharedViewModel? = null
    protected val mainScreenSharedViewModel
        get() = requireNotNull(_mainScreenSharedViewModel) { "Shared ViewModel isn't init" }

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDaggerComponent()
        _mainScreenSharedViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainScreenSharedViewModel::class.java]
    }

    abstract fun initDaggerComponent()

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

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyFragmentView()
        _binding = null
    }

    abstract fun onDestroyFragmentView()

    abstract fun createBinding(view: View): VB
}
