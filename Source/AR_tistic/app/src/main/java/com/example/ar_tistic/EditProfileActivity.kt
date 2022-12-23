package com.example.ar_tistic

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ar_tistic.MainActivity.Companion.manager
import com.example.classlib.*
import java.io.ByteArrayOutputStream
import java.util.*


class EditProfileActivity : AppCompatActivity() {
    //lateinit var manager:Manager
    lateinit var pp: ImageView

    private val GALLERY_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        getInfos()
        var retMap=findViewById<ImageButton>(R.id.returnMapPage)
        retMap.setOnClickListener(){
            returnMap()
        }
        var upload=findViewById<Button>(R.id.upload)
        upload.setOnClickListener{
            openGallery()
        }
    }
    fun getInfos(){
        var email=findViewById<TextView>(R.id.emailTxtView)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var birthDate=findViewById<TextView>(R.id.bdTxtView)
        val intent = intent
        //Get manager
        //manager = intent.getSerializableExtra("manager") as Manager
        var usr:User=manager.usr
        email.text=usr.email
        name.text=usr.name
        birthDate.text=usr.birthDate.day.toString()+"-"+usr.birthDate.month.toString()+"-"+usr.birthDate.year.toString()
        if(manager.usr.profilePicture!=""){
            val imageView = findViewById<ImageView>(R.id.imageView2)
            var img=convertX64toImg(manager.usr.profilePicture)
            imageView.setImageBitmap(img)
        }
    }
    fun returnMap(){
        finish()
    }
    fun convertImgToX64(bitmap: Bitmap):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        var imageBytes = byteArrayOutputStream.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }
    fun convertX64toImg(imageString:String):Bitmap{
        var imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return decodedImage
    }
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val imageView = findViewById<ImageView>(R.id.imageView2)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            val x64=convertImgToX64(bitmap)
            manager.usr.profilePicture=x64
            //manager.persistence.updateUser(manager.usr.id,manager.usr)
            //{AXEL} : ca crash prck il faut le passer en thread (je pense)
            val img=convertX64toImg(x64)
            imageView.setImageBitmap(img)
        }
    }
}