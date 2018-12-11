package com.xjm.wanandroid.widget

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.xjm.wanandroid.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp


/**
 * Created by xjm on 2018/12/7.
 */
class TimePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }
    private val mTextPaint by lazy { Paint() }

    private val textFontMetrics by lazy { mTextPaint.fontMetrics }
    private val textRect by lazy { RectF() }

    private var mCenterX = 0f
    private var mCenterY = 0f

    private var mBgRadius = 0f

    private var mInsideRadius = 0f
    private var mOutsideRadius = 0f

    var count = 12

    private var mEachAngle = 0.0

    private var mDefTextColor = 0
    private var mSelTextColor = 0
    private var mBgColor = 0
    private var mSelColor = 0

    private var mItemRadius = 0f

    private var mPoint = PointF(0f, 0f)

    private var mStep = 1

    private val selectPath = Path()

    private var isMoveInSide = false

    private var limitLength = 0f

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimePickerView)

        mDefTextColor = typedArray.getColor(R.styleable.TimePickerView_defTextColor, Color.BLACK)
        mSelTextColor = typedArray.getColor(R.styleable.TimePickerView_selTextColor, Color.WHITE)
        mSelColor = typedArray.getColor(R.styleable.TimePickerView_selColor, Color.CYAN)
        mBgColor = typedArray.getColor(R.styleable.TimePickerView_bgColor, Color.LTGRAY)

        typedArray.recycle()

        initView()
    }

    private fun initView() {
        mEachAngle = 360.0 / count
        mPaint.isAntiAlias = true
        mTextPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mCenterX = w / 2f
        mCenterY = h / 2f
        mBgRadius = 2 * w / 5f
        mItemRadius = mBgRadius / 9f

        mInsideRadius = 3f / 5 * mBgRadius
        mOutsideRadius = 4f / 5 * mBgRadius

        limitLength = (mInsideRadius + mOutsideRadius) / 2f
    }

    override fun onDraw(canvas: Canvas) {

        drawBackCircle(canvas)
        drawCenterCircle(canvas)
        drawLineAndSelCircle(canvas)
        drawText(canvas)

    }

    private fun drawText(canvas: Canvas) {
        //默认颜色文字
        drawItemText(canvas)

        //选中颜色文字
        canvas.save()
        canvas.clipPath(selectPath)
        drawItemText(canvas, true)
        canvas.restore()
    }

    private fun drawLineAndSelCircle(canvas: Canvas) {
        mPaint.color = mSelColor
        mPaint.strokeWidth = dip(2).toFloat()
        val point = getItemPointByAngle(getAngleByPoint(mPoint), isMoveInSide)
        canvas.drawLine(mCenterX, mCenterY, point.x, point.y, mPaint)
        canvas.drawCircle(point.x, point.y, mItemRadius, mPaint)

        selectPath.reset()
        selectPath.addCircle(point.x, point.y, mItemRadius, Path.Direction.CCW)
    }

    private fun drawCenterCircle(canvas: Canvas) {
        mPaint.color = mSelColor
        canvas.drawCircle(mCenterX, mCenterY, dip(5).toFloat(), mPaint)
    }

    private fun drawBackCircle(canvas: Canvas) {
        mPaint.color = mBgColor
        canvas.drawCircle(mCenterX, mCenterY, mBgRadius, mPaint)
    }

    private fun drawItemText(canvas: Canvas, isSelected: Boolean = false) {
        mPaint.color = mSelColor
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mTextPaint.apply {
            color = if (isSelected) mSelTextColor else mDefTextColor
            textAlign = Paint.Align.CENTER
            textSize = sp(16).toFloat()
        }
        var x: Float
        var y: Float
        for (i in 0 until 2 * count step mStep) {
            val point = getItemPointByAngle((i * mEachAngle), i >= count)
            x = point.x
            y = point.y

            textRect.apply {
                left = x - mItemRadius
                top = y - mItemRadius
                right = x + mItemRadius
                bottom = y + mItemRadius
            }
            val top = textFontMetrics.top
            val bottom = textFontMetrics.bottom
            val baseLineY = textRect.centerY() - top / 2 - bottom / 2
            val text = (when (i) {
                0 -> 12
                12 -> 0
                else -> i
            }).toString()

            canvas.drawText(text, textRect.centerX(), baseLineY, mTextPaint)
        }
    }

    private fun getItemPointByAngle(angle: Double, isInside: Boolean = false): PointF {
        val pointF = PointF()
        pointF.x = (mCenterX + (if (isInside) mInsideRadius else mOutsideRadius) * Math.sin(Math.toRadians(angle)).toFloat())
        pointF.y = (mCenterY - (if (isInside) mInsideRadius else mOutsideRadius) * Math.cos(Math.toRadians(angle)).toFloat())
        return pointF
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val angle = getAngleByPoint(PointF(x, y))
        isMoveInSide = Math.sqrt(Math.pow((x - mCenterX).toDouble(), 2.0) + Math.pow((y - mCenterY).toDouble(), 2.0)) < limitLength
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                mPoint.let {
                    it.x = x
                    it.y = y
                }
            }
            MotionEvent.ACTION_UP -> {
                val curIndex: Int = (angle / mEachAngle).toInt() + if ((angle % mEachAngle) <= 15) 0 else 1
                val point = getItemPointByAngle(curIndex * mEachAngle, isMoveInSide)
                startAnimator(point)
            }
        }
        invalidate()
        return true
    }

    private fun getAngleByPoint(point: PointF): Double {
        val xAbs = Math.abs(point.x - mCenterX)
        val yAbs = Math.abs(point.y - mCenterY)
        val degrees = Math.toDegrees(Math.atan((xAbs / yAbs).toDouble()))
        if (point.x == mCenterX && point.y < mCenterY) {     // y正轴
            return 0.0
        } else if (point.x > mCenterX && point.y < mCenterY) {   //第一象限
            return degrees
        } else if (point.x < mCenterX && point.y < mCenterY) {   //第二象限
            return 360 - degrees
        } else if (point.x == mCenterX && point.y > mCenterY) {   // y负轴
            return 180.0
        } else if (point.x < mCenterX && point.y > mCenterY) {   //第三象限
            return 180 + degrees
        } else if (point.x > mCenterX && point.y > mCenterY) {   //第四象限
            return 180 - degrees
        } else if (point.x > mCenterX && point.y == mCenterY) {   // x正轴
            return 90.0
        } else if (point.x < mCenterX && point.y == mCenterY) {   // x负轴
            return 270.0
        }
        return 0.0
    }

    private fun startAnimator(endpoint: PointF) {
        val anim = ValueAnimator.ofObject(PointEvaluator(), mPoint, endpoint)
        anim.addUpdateListener {
            mPoint = it.animatedValue as PointF
            invalidate()
        }
        anim.duration = 100
        anim.start()
    }

    inner class PointEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val x = startValue.x + fraction * (endValue.x - startValue.x)
            val y = startValue.y + fraction * (endValue.y - startValue.y)
            return PointF(x, y)
        }
    }


}