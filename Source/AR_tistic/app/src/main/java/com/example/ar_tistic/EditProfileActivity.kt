package com.example.ar_tistic

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.classlib.*

class EditProfileActivity : AppCompatActivity() {
    lateinit var manager:Manager
    lateinit var button: Button
    lateinit var pp: ImageView

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        getInfos()
        var retMap=findViewById<ImageButton>(R.id.returnMapPage)
        retMap.setOnClickListener(){
            returnMap()
        }
        var upload=findViewById<Button>(R.id.upload)
        button.setOnClickListener{
            pickImageGallery()
            /*Press alt enter*/
        }
        upload.setOnClickListener(){
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 777)
        }
    }
    fun getInfos(){
        var email=findViewById<TextView>(R.id.emailTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var birthDate=findViewById<TextView>(R.id.bdTxtView)
        val intent = intent
        //Get manager
        manager = intent.getSerializableExtra("manager") as Manager
        var usr:User=manager.usr
        email.text=usr.email
        name.text=usr.name
        birthDate.text=usr.birthDate.day.toString()+"-"+usr.birthDate.month.toString()+"-"+usr.birthDate.year.toString()
        button=findViewById(R.id.upload)
        pp=findViewById(R.id.imageView2)
        //pp.setImageResource(R.drawable.at_tistic_logo_framed_reverse) set thye srce
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
            pp.setImageURI(data?.data)
        }
    }
}