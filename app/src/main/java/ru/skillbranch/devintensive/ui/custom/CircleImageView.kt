package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.dpToPx
import ru.skillbranch.devintensive.extensions.pxToDp

class CircleImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        // size in dp
        private const val DEFAULT_SIZE = 40
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    @Px
    var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)
    @ColorInt
    private var borderColor: Int = Color.WHITE

    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val viewRect = Rect()

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderWidth =
                    ta.getDimension(
                            R.styleable.CircleImageView_cv_borderWidth,
                            context.dpToPx(DEFAULT_BORDER_WIDTH)
                    )
            borderColor = ta.getColor(R.styleable.CircleImageView_cv_borderColor,
                    DEFAULT_BORDER_COLOR
            )

            ta.recycle()
        }

        scaleType = ScaleType.CENTER_CROP
        setup()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d("M_CircleImageView", "onAttachedToWindow")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(
                "M_CircleImageView", """
                    onMeasure
                    width: ${MeasureSpec.toString(widthMeasureSpec)}
                    height: ${MeasureSpec.toString(heightMeasureSpec)}
                    """.trimIndent()
        )

        val initSize: Int = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)

        Log.d("M_CircleImageView", "onMeasure after set size: $measuredWidth $measuredHeight")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d("M_CircleImageView", "onSizeChanged")

        if (w == 0) return

        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }

        prepareShader(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("M_CircleImageView", "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        Log.d("M_CircleImageView", "onDraw")

        canvas?.drawOval(viewRect.toRectF(), avatarPaint)
        // resize rect for border
        val half = (borderWidth / 2).toInt()
        viewRect.inset(half, half)
        canvas?.drawOval(viewRect.toRectF(), borderPaint)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        Log.d("M_AvatarImageView", "setBorderColor")

        borderColor = resources.getColor(colorId, context.theme)
        borderPaint.color = borderColor
        invalidate()
    }

    fun setBorderColor(hex: String) {
        Log.d("M_AvatarImageView", "setBorderColor")
        borderColor = Color.parseColor(hex)
        borderPaint.color = borderColor
        invalidate()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderWidth(@Dimension dp: Int) {
        Log.d("M_AvatarImageView", "setBorderWidth")
        borderWidth = context.dpToPx(dp)
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    @Dimension
    fun getBorderWidth(): Int {
        return context.pxToDp(borderWidth)
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun setup() {
        with(borderPaint) {
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    private fun prepareShader(w: Int, h: Int) {
        val srcBm = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)

        avatarPaint.shader = BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
}