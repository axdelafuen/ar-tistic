package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.classlib.Date
import com.example.classlib.Manager
import com.example.classlib.User

class EditProfileActivity : AppCompatActivity() {
    lateinit var manager: Manager
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        manager = intent.getSerializableExtra("manager") as Manager
        getInfos()
        var retMap=findViewById<ImageButton>(R.id.returnMapPage)
        retMap.setOnClickListener(){
            returnMap()
        }
    }

    fun getInfos(){
        var email=findViewById<TextView>(R.id.email)
        var name=findViewById<TextView>(R.id.nameTxtView)
        var date=findViewById<TextView>(R.id.bdTxtView)
        val intent = intent
        //Get manager
        manager = intent.getSerializableExtra("manager") as Manager
        var usr: User =manager.usr
        email.text=manager.usr.email
        name.text=manager.usr.name
        date.text=manager.usr.birthDate.day.toString()+"-"+manager.usr.birthDate.month.toString()+"-"+manager.usr.birthDate.year.toString()
        imageView=findViewById(R.id.imageView2)
    }
    fun returnMap(){
        finish()
    }
    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, ProfilActivity.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== ProfilActivity.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }
}