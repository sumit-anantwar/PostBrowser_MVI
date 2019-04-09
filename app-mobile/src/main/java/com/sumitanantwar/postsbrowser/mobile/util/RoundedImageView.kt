package com.sumitanantwar.postsbrowser.mobile.util

import android.graphics.RectF
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView
import com.sumitanantwar.postsbrowser.mobile.R


class RoundedImageView : ImageView {

    private var cornerRadius: Int = 0
    private val framePath: Path
    private val rectF = RectF()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyleAttr, 0)

        cornerRadius = attributes.getDimensionPixelSize(R.styleable.RoundedImageView_corner_radius, 0)

        attributes.recycle()

        framePath = Path()

    }

    override fun onDraw(canvas: Canvas) {

        val crf = cornerRadius.toFloat()

        framePath.reset()
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        framePath.addRoundRect(rectF, crf, crf, Path.Direction.CCW)

        canvas.clipPath(framePath)

        super.onDraw(canvas)
    }
}