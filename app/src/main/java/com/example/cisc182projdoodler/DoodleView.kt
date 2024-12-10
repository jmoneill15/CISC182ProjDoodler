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

    private val pathList = mutableListOf<Path>() // List of drawn paths
    private val paintList = mutableListOf<Paint>() // List of Paint objects for the drawn paths
    private val undonePaths = mutableListOf<Pair<Path, Paint>>() // Stores undone paths with their Paint objects

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
        // Draw all saved paths
        pathList.forEachIndexed { index, path ->
            canvas.drawPath(path, paintList[index])
        }
        // Draw the current path being drawn
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                currentPath.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                // Finalize the current path and add it to the path list
                currentPath.lineTo(event.x, event.y)
                pathList.add(currentPath)
                paintList.add(Paint(currentPaint)) // Ensure the paint is correctly paired
                currentPath = Path() // Reset the current path for the next stroke
                undonePaths.clear() // Clear undone paths after a new stroke
            }
        }
        invalidate() // Trigger redraw after any touch event
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
        if (pathList.isNotEmpty() && paintList.isNotEmpty()) {
            // Remove the last path and paint and add to the undone stack
            val lastPath = pathList.removeAt(pathList.lastIndex)
            val lastPaint = paintList.removeAt(paintList.lastIndex)
            undonePaths.add(lastPath to lastPaint)
            invalidate() // Redraw without the last path
        }
    }


    fun redo() {
        if (undonePaths.isNotEmpty()) {
            // Restore the last undone path and its Paint object
            val (path, paint) = undonePaths.removeAt(undonePaths.lastIndex)
            pathList.add(path)
            paintList.add(paint)
            invalidate()
        }
    }

    fun clearCanvas() {
        pathList.clear()
        paintList.clear()
        undonePaths.clear()
        currentPath.reset()
        invalidate()
    }
}
