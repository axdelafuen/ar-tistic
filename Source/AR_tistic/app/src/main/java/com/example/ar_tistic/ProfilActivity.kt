package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.classlib.Date
import com.example.classlib.User

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
        var email=findViewById<TextView>(R.id.emailTxtView)
        var pswdA=findViewById<TextView>(R.id.psswdTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var birthDate=findViewById<TextView>(R.id.bdTxtView)



        val intent = intent
        // passer le manager
        val cttemail = intent.getSerializableExtra("email") as String
        val cttpswd = intent.getSerializableExtra("pswd") as String


        var usr=createUser(cttemail,cttpswd)

        email.text=cttemail
        name.text=usr.name
        birthDate.text=usr.birthDate.day.toString()+"-"+usr.birthDate.month.toString()+"-"+usr.birthDate.year.toString()

        pswdA.text=cttpswd
    }
    fun returnMap(){
        val intent = Intent(applicationContext,MapActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun createUser(email:String, pswd:String):User{
        var id=1//Id from manager
        val ppDefault="/img/ppDefault"
        return User(id, "User"+id, "",email,pswd, Date(1999,2,2),hashMapOf(),0)
    }

}