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
import kotlin.math.abs


class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint : Paint?=null
    private var path: android.graphics.Path?=null
    private var pathlist = ArrayList<PaintPath>()
    private var undonePathList = ArrayList<PaintPath>()
    private var mPath:android.graphics.Path?=null
    private var mX:Float?=null
    private var mY:Float?=null
    private var TouchTolerance:Float? = 4f

    init {
        paint= Paint()
        paint!!.color= Color.GREEN
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
        val yPos : Float=event.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                touchStart(xPos,yPos)
            }
            MotionEvent.ACTION_MOVE->{
                touchMove(xPos,yPos)
            }
            MotionEvent.ACTION_UP->{
                touchUp()
            }
            else->{

            }
        }
        invalidate()
        return true
    }
    private fun touchStart(xPos: Float, yPos: Float) {
        mPath=android.graphics.Path()
        val paintPath=PaintPath(mPath!!)
        pathlist.add(paintPath)
        mPath!!.reset()
        mPath!!.moveTo(xPos,yPos)
        mX=xPos
        mY=yPos
    }
    private fun touchMove(xPos: Float, yPos: Float) {
        val dX:Float = abs(xPos-mX!!)
        val dY:Float = abs(yPos-mY!!)

    }

    private fun touchUp(){

    }
}
