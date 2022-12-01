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
    lateinit var selectedUsr: User
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    @SuppressLint("MissingInflatedId")
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
        selectedUsr= User(id = 4, name = "Louis", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password ="aaaa123", birthDate = Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 )
        var nbAbo=findViewById<TextView>(R.id.followTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var nbLikes=findViewById<TextView>(R.id.likeTxtView)
        val intent = intent
        //Get manager
        manager = intent.getSerializableExtra("manager") as Manager
        var usr:User=manager.usr
        nbAbo.text=manager.persistence.getFollowers(selectedUsr.id).toString()
        name.text=selectedUsr.name
        nbLikes.text=manager.persistence.getLikes(selectedUsr.id).toString()
        imageView=findViewById(R.id.imageView2)
    }
    fun returnMap(){
        finish()
    }
    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }
}