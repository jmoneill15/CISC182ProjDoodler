package com.example.cisc182projdoodler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.util.AttributeSet

class DoodleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context,attrs, defStyleAttr){

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()

        canvas?.drawLine(0f,0f,300f,500f,paint)
    }
}



