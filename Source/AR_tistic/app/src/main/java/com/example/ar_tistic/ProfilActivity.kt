package com.example.ar_tistic

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.classlib.*

class ProfilActivity : AppCompatActivity() {
    lateinit var manager:Manager
    lateinit var imageView: ImageView
    var idUsr=1

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

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
        var nbAbo=findViewById<TextView>(R.id.followTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var nbLikes=findViewById<TextView>(R.id.likeTxtView)
        val intent = intent
        //Get manager
        manager = intent.getSerializableExtra("manager") as Manager
        idUsr = intent.getSerializableExtra("id_usr") as Int
        name.text=manager.persistence.getUserById(idUsr)?.name.toString()
        nbAbo.text=manager.persistence.getNbFollowers(idUsr)?.toString()
        nbLikes.text=manager.persistence.getLikes(idUsr)?.toString()
        imageView=findViewById(R.id.imageView2)
    }
    fun returnMap(){
        finish()
    }
}