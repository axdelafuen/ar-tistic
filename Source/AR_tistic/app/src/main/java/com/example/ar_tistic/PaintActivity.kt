package com.example.ar_tistic

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.ar_tistic.PaintView.Companion.colorsList
import com.example.ar_tistic.PaintView.Companion.pathList
import com.example.ar_tistic.R

class PaintActivity : AppCompatActivity() {

    companion object{
        var path = Path()
        var paintBrush = Paint()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        val redBtn = findViewById<ImageButton>(R.id.red_color)
        val blueBtn = findViewById<ImageButton>(R.id.blue_color)
        val yellowBtn = findViewById<ImageButton>(R.id.yellow_color)
        val orangeBtn = findViewById<ImageButton>(R.id.orange_color)
        val eraser = findViewById<ImageButton>(R.id.white_color)
        supportActionBar?.hide()

        redBtn.setOnClickListener {
            paintBrush.color = Color.parseColor("#FF0000")
            currentColor(paintBrush.color)
        }
        blueBtn.setOnClickListener {
            paintBrush.color = Color.parseColor("#0000FF")
            currentColor(paintBrush.color)
        }
        yellowBtn.setOnClickListener {
            paintBrush.color = Color.parseColor("#FFFF00")
            currentColor(paintBrush.color)
        }
        orangeBtn.setOnClickListener {
            paintBrush.color = Color.parseColor("#FF8000")
            currentColor(paintBrush.color)
        }
        eraser.setOnClickListener {
            paintBrush.color = Color.parseColor("#FFFFFFFF")
            currentColor(paintBrush.color)
        }
    }

    private fun currentColor(color: Int){
        PaintView.currentBrush = color
        colorsList.add(color)
        path = Path()
        pathList.add(path)

    }

    private fun setStrokeWidth(width: Float){
        paintBrush.strokeWidth = width
    }

}