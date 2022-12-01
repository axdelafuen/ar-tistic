package com.example.ar_tistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classlib.*
import android.content.Intent
import android.widget.ImageButton

class LeaderBoardActivity : AppCompatActivity() {
    lateinit var manager:Manager
    lateinit var podium: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        manager = intent.getSerializableExtra("manager") as Manager
        createButtons()
        getPodium()
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
        val mesageBtn = findViewById<ImageButton>(R.id.msgButton)
        mesageBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
            finish()
        }
        val mapBtn = findViewById<ImageButton>(R.id.mapButton)
        mapBtn.setOnClickListener {
            finish()
        }
        val usr1 = findViewById<ImageButton>(R.id.btnAccess1)
        usr1.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            intent.putExtra("manager", manager)
            intent.putExtra("id_usr", manager)
            startActivity(intent)
            finish()
        }
        val usr2 = findViewById<ImageButton>(R.id.btnAccess2)
        val usr3 = findViewById<ImageButton>(R.id.btnAccess3)

    }
    fun getPodium(){

    }
}