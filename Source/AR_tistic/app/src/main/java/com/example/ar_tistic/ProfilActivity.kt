package com.example.ar_tistic

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.classlib.*

class ProfilActivity : AppCompatActivity() {
    lateinit var usr:User
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
        var email=findViewById<TextView>(R.id.emailTxtView)
        var pswdA=findViewById<TextView>(R.id.psswdTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var birthDate=findViewById<TextView>(R.id.bdTxtView)



        val intent = intent
        // passer le manager
        usr = intent.getSerializableExtra("usr") as User
        email.text=usr.email
        name.text=usr.name
        birthDate.text=usr.birthDate.day.toString()+"-"+usr.birthDate.month.toString()+"-"+usr.birthDate.year.toString()
        pswdA.text=usr.password
    }
    fun returnMap(){
        val intent = Intent(applicationContext,MapActivity::class.java)
        intent.putExtra("usr", usr)
        startActivity(intent)
        finish()
    }
}