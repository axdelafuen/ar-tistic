package com.example.ar_tistic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.moveTo


class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint : Paint?=null
    private var path: android.graphics.Path?=null
    init {
        paint= Paint()
        path = android.graphics.Path()
        paint!!.color= Color.RED
        paint!!.strokeWidth=10f
        paint!!.style=Paint.Style.STROKE
        paint!!.isAntiAlias=true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?){
        paint?.let { canvas!!.drawPath(path!!, it) }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val xPos : Float=event!!.x
        val yPos : Float=event!!.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                path!!.moveTo(xPos,yPos)
            }
            MotionEvent.ACTION_MOVE->{
                path!!.lineTo(xPos,yPos)

            }
            MotionEvent.ACTION_UP->{
            }
            else->{

            }
        }
        invalidate()
        return true
    }
}
