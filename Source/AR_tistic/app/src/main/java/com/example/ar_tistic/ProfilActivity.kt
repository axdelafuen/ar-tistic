package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        getInfos()
        var retMap=findViewById<ImageButton>(R.id.returnMapPage)
        retMap.setOnClickListener(){
            returnMap()
        }

    }
    fun getInfos(){
        var nomA=findViewById<TextView>(R.id.nameTxtView)
        var pswdA=findViewById<TextView>(R.id.psswdTxtView)
        val intent = intent
        val email = intent.getSerializableExtra("email") as String?
        val pswd = intent.getSerializableExtra("pswd") as String?
        nomA.text=email
        pswdA.text=pswd
    }
    fun returnMap(){
        val intent = Intent(applicationContext,MapActivity::class.java)
        startActivity(intent)
        finish()
    }

}