package com.example.ar_tistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        getInfos()
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

}