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
    override fun onDraw(canvas: Canvas?){
        if(pathlist.size>0){
            for(path in pathlist){
                paint?.let { canvas!!.drawPath(path.path, it) }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val xPos : Float=event!!.x
        val yPos : Float=event.y
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                touchStart(xPos,yPos)
                invalidate()
            }
            MotionEvent.ACTION_MOVE->{
                touchMove(xPos,yPos)
                invalidate()
            }
            MotionEvent.ACTION_UP->{
                touchUp()
                invalidate()
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
        if(dX> TouchTolerance!! || dY>TouchTolerance!!){
            mPath!!.quadTo(mX!!,mY!!,(xPos+mX!!)/2,(yPos+mY!!)/2)
            mX=xPos
            mY=yPos
        }
    }

    private fun touchUp(){
        mPath!!.lineTo(mX!!,mY!!)
    }
    fun setUndo(){
        val size = pathlist.size
        if(pathlist.size>0){
            undonePathList.add(pathlist[size-1])
            pathlist.removeAt(size-1)
            invalidate()
        }
    }
    fun setRedo(){
        val size = undonePathList.size
        if(size>0){
            pathlist.add(undonePathList[size-1])
            undonePathList.removeAt(size-1)
            invalidate()
        }
    }
    fun setDelete(){
        if(pathlist.size>0){
            pathlist.clear()
            undonePathList.clear()
            invalidate()
        }
    }
}
