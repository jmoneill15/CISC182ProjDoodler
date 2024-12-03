package com.example.cisc182projdoodler

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.ColorUtils

class DoodleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val pathList = mutableListOf<Path>()
    private val undonePaths = mutableListOf<Path>()
    private val paintList = mutableListOf<Paint>()

    private var currentPath = Path()
    private val currentPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathList.forEachIndexed { index, path ->
            canvas.drawPath(path, paintList[index])
        }
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                currentPath.moveTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                // Save the path and the corresponding paint object
                pathList.add(currentPath)
                paintList.add(Paint(currentPaint))  // Add a new Paint instance with the current color and settings
                undonePaths.clear()  // Clear the undone paths since we can't redo after a new action
            }
        }
        return true
    }

    fun setBrushSize(size: Float) {
        currentPaint.strokeWidth = size
    }

    fun setColor(color: Int) {
        currentPaint.color = color
        invalidate()
    }

    fun setOpacity(opacity: Int) {
        val colorWithAlpha = ColorUtils.setAlphaComponent(currentPaint.color, opacity)
        currentPaint.color = colorWithAlpha
        invalidate()
    }

    fun undo() {
        if (pathList.isNotEmpty()) {
            undonePaths.add(pathList.removeAt(pathList.lastIndex))
            paintList.removeAt(paintList.lastIndex)  // Remove the corresponding paint for the undone path
            invalidate()
        }
    }

    fun redo() {
        if (undonePaths.isNotEmpty()) {
            pathList.add(undonePaths.removeAt(undonePaths.lastIndex))
            // The paint object for the redo operation should match the paint at the time of drawing.
            paintList.add(Paint(currentPaint))  // Clone the current paint for the redo path
            invalidate()
        }
    }

    fun clearCanvas() {
        pathList.clear()
        undonePaths.clear()
        paintList.clear()
        invalidate()
    }
}

/*
class DoodleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val pathList = mutableListOf<Path>()
    private val undonePaths = mutableListOf<Path>()
    private val paintList = mutableListOf<Paint>()

    private var currentPath = Path()
    private val currentPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathList.forEachIndexed { index, path ->
            canvas.drawPath(path, paintList[index])
        }
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                currentPath.moveTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                pathList.add(currentPath)
                paintList.add(Paint(currentPaint))
                undonePaths.clear()
            }
        }
        return true
    }

    fun setBrushSize(size: Float) {
        currentPaint.strokeWidth = size
    }

    fun setColor(color: Int) {
        currentPaint.color = color
        invalidate()
    }

    fun setOpacity(opacity: Int) {
        val colorWithAlpha = ColorUtils.setAlphaComponent(currentPaint.color, opacity)
        currentPaint.color = colorWithAlpha
        invalidate()
    }

    fun undo() {
        if (pathList.isNotEmpty()) {
            undonePaths.add(pathList.removeAt(pathList.lastIndex))
            paintList.removeAt(paintList.lastIndex)
            invalidate()
        }
    }

    fun redo() {
        if (undonePaths.isNotEmpty()) {
            pathList.add(undonePaths.removeAt(undonePaths.lastIndex))
            paintList.add(Paint(currentPaint))
            invalidate()
        }
    }

    fun clearCanvas() {
        pathList.clear()
        undonePaths.clear()
        paintList.clear()
        invalidate()
    }
}
*/