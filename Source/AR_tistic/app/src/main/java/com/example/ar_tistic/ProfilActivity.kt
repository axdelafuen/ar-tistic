package com.example.ar_tistic

import User
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.classlib.*

class ProfilActivity : AppCompatActivity() {
    lateinit var manager:Manager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        getInfos()
        var retMap=findViewById<ImageButton>(R.id.returnMapPage)
        retMap.setOnClickListener(){
            returnMap()
        }
        var upload=findViewById<Button>(R.id.upload)
        upload.setOnClickListener(){
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 777)
        }
        //PP
        val pp=findViewById<ImageView>(R.id.imageView2)
        pp.setBackgroundResource(manager.usr.profilePicture.toInt())

    }
    fun getInfos(){
        var email=findViewById<TextView>(R.id.emailTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var birthDate=findViewById<TextView>(R.id.bdTxtView)
        val intent = intent
        //Get manager
        //manager = intent.getSerializableExtra("manager") as Manager
        manager = intent.getSerializableExtra("manager") as Manager
        var usr:User=manager.usr
        email.text=usr.email
        name.text=usr.name
        birthDate.text=usr.birthDate.day.toString()+"-"+usr.birthDate.month.toString()+"-"+usr.birthDate.year.toString()
    }
    fun returnMap(){
        finish()
    }
}