package com.redmechax00.astonintensivecourseproject.presentation.customviews.textviewoutline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.redmechax00.astonintensivecourseproject.R

class TextViewOutline @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val outlineColor: Int
    private val outlineWidth: Float

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TextViewOutline, 0, 0)
        try {
            outlineColor = attributes.getInt(R.styleable.TextViewOutline_outline_color, Color.BLACK)
            outlineWidth = attributes.getFloat(R.styleable.TextViewOutline_outline_width, 0f)
        } finally {
            attributes.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint: Paint = paint
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = paint.textSize * (outlineWidth / 100)
        val colorTmp: Int = paint.color
        setTextColor(outlineColor)
        super.onDraw(canvas)
        setTextColor(colorTmp)
        paint.style = Paint.Style.FILL
        postInvalidateDelayed(200)
    }
}