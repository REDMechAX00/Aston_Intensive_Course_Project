package com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.details.base

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import androidx.viewbinding.ViewBinding
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.presentation.screens.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseDetailsFragment<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>
) : BaseFragment<VB>(layoutRes) {

    private var _detailsViewModel: VM? = null
    protected val detailsViewModel
        get() = requireNotNull(_detailsViewModel) { "$viewModelClass ViewModel isn't init" }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _detailsViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[viewModelClass]
    }

    abstract fun observeViewModel()

    abstract fun setListeners()

    override fun onDestroyFragmentView() {}

    protected fun startDetailsFragment(fragmentTag: String, detailsId: Int) {
        mainScreenSharedViewModel.onStartDetailsFragment(fragmentTag, detailsId)
    }

    protected fun getIdFromDetailsUrl(pathWithoutId: String, pathWithId: String): Int? {
        val pathSegmentsWithoutId = Uri.parse(pathWithoutId).pathSegments.toMutableList()
        val pathSegmentsWithId = Uri.parse(pathWithId).pathSegments.toMutableList()

        return if (pathSegmentsWithId.size - pathSegmentsWithoutId.size != 1) null
        else {
            pathSegmentsWithoutId.forEachIndexed { i, pathSegment ->
                val isSegmentsSame = pathSegment == pathSegmentsWithId[i]
                if (!isSegmentsSame) return null
            }
            pathSegmentsWithId.last().toIntOrNull()
        }
    }

    protected fun loadImageAndDominantColor(
        imageUrl: String,
        onSuccess: (image: Bitmap, dominantColor: Int) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            val loader = ImageLoader(requireContext())
            val request = ImageRequest.Builder(requireContext())
                .data(imageUrl)
                .allowHardware(false)
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            launch(Dispatchers.Main) {
                Palette.Builder(bitmap).generate {
                    it?.let { palette ->
                        val dominantColor = palette.getDominantColor(
                            ContextCompat.getColor(requireContext(), R.color.color_blue)
                        )
                        onSuccess(bitmap, dominantColor)
                    }
                }
            }
        }
    }

    protected fun Bitmap.cropCenter(targetView: View): Bitmap {
        return ThumbnailUtils.extractThumbnail(this, targetView.width, targetView.height)
    }

    protected fun Bitmap.setBottomAlphaGradientFrame(): Bitmap {
        val mutableBitmap: Bitmap = this.copy(Bitmap.Config.ARGB_8888, true)
        val framePaint = Paint()
        val canvas = Canvas(mutableBitmap)
        setBottomAlphaFramePaint(framePaint, this.width.toFloat(), this.height.toFloat())
        canvas.drawPaint(framePaint)
        return mutableBitmap
    }

    private fun setBottomAlphaFramePaint(p: Paint, bitmapWidth: Float, bitmapHeight: Float) {
        p.shader = null
        p.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        val borderSize = 0.05f
        val bSize =
            if (bitmapWidth > bitmapHeight) bitmapHeight * borderSize else bitmapHeight * borderSize

        p.shader = LinearGradient(
            bitmapWidth / 2,
            bitmapHeight,
            bitmapWidth / 2,
            bitmapHeight - bSize,
            Color.TRANSPARENT,
            Color.BLACK,
            Shader.TileMode.CLAMP
        )

    }
}