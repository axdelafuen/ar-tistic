package com.example.ar_tistic

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.example.classlib.Manager

class CanvaActivity : AppCompatActivity() {
    lateinit var manager: Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager=intent.getSerializableExtra("manager") as Manager
        setContentView(R.layout.activity_canva)
        createButtons()
        val ControlUndo = findViewById<ImageView>(R.id.ControlUndo)
        var PaintArea = findViewById<com.example.ar_tistic.DrawPath>(R.id.PaintArea)
        ControlUndo.setOnClickListener{
            PaintArea.setUndo()
        }
        val ControlRedo = findViewById<ImageView>(R.id.ControlRedo)
        ControlRedo.setOnClickListener{
            PaintArea.setRedo()
        }
        val ControlFlush = findViewById<ImageView>(R.id.ControlFlush)
        ControlFlush.setOnClickListener{
            PaintArea.setDelete()
        }
    }
    fun createButtons(){
        val paintBtn = findViewById<ImageButton>(R.id.drawButton)
        paintBtn.setOnClickListener {
            val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)
            finish()
        }
        val ratingBtn = findViewById<ImageButton>(R.id.leaderboardButton)
        ratingBtn.setOnClickListener {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
            finish()
        }
        val messageBtn = findViewById<ImageButton>(R.id.msgButton)
        messageBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
            finish()
        }
        val mapBtn = findViewById<ImageButton>(R.id.mapButton)
            mapBtn.setOnClickListener {
            finish()
        }
    }
}