package com.example.ar_tistic

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.ImageButton
import android.widget.ImageView
import com.example.ar_tistic.MainActivity.Companion.manager
import com.example.classlib.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class DrawsVisualisation : AppCompatActivity() {
    lateinit var draws:ArrayList<Draw>
    lateinit var interestPoint:InterestPoint

    private val GALLERY_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draws_visualisation)
        createButtons()
        val id=0
        interestPoint= manager.persistence.getInterestPointById(id)
        draws=manager.persistence.getDrawsInInterestPoint(interestPoint.id)
        //buttons
        val previous = findViewById<ImageView>(R.id.previous)
        val nextt = findViewById<ImageView>(R.id.next)
        val add = findViewById<ImageView>(R.id.addDrawing)
        val report = findViewById<ImageView>(R.id.report)
        val like = findViewById<ImageView>(R.id.liked)
        previous.setOnClickListener{
            previous()
        }
        nextt.setOnClickListener{
            nextt()
        }
        add.setOnClickListener{
            add()
        }
        report.setOnClickListener{
            report()
        }
        like.setOnClickListener{
            like()
        }
    }


    fun createButtons() {//create navbar buttons
        val paintBtn = findViewById<ImageButton>(R.id.drawButton)
        paintBtn.setOnClickListener {
            val intent = Intent(this, CanvaActivity::class.java)
            intent.putExtra("manager", MainActivity.manager)
            startActivity(intent)
            finish()
        }
        val ratingBtn = findViewById<ImageButton>(R.id.leaderboardButton)
        ratingBtn.setOnClickListener {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            intent.putExtra("manager", MainActivity.manager)
            startActivity(intent)
            finish()
        }
        val mesageBtn = findViewById<ImageButton>(R.id.msgButton)
        mesageBtn.setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java)
            intent.putExtra("manager", MainActivity.manager)
            startActivity(intent)
            finish()
        }
        val mapBtn = findViewById<ImageButton>(R.id.mapButton)
        mapBtn.setOnClickListener {
            finish()
        }
    }
    fun previous(){
        return
    }
    fun nextt(){
        return
    }
    fun add(){
        openGallery()
        setImages()
    }
    fun report(){
        return
    }
    fun like(){
        return
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
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            val x64=convertImgToX64(bitmap)
            //GlobalScope.launch {//add the draw in pers
                manager.persistence.createDraw(Draw(0,
                    "draw",
                    x64,
                    hashMapOf(0 to interestPoint),
                    Date(0,0,0),
                    Time(99,99,99),
                    hashMapOf(manager.usr.id to manager.usr),
                    0,
                    0
                ))
                println("Done")
            //}
            //GlobalScope.launch {//get draw from pers
                draws=manager.persistence.getDrawsInInterestPoint(interestPoint.id)
            //}
        }
    }
    fun convertImgToX64(bitmap: Bitmap):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        var imageBytes = byteArrayOutputStream.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }
    fun convertX64toImg(imageString:String): Bitmap {
        var imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)!!
        return decodedImage
    }
    fun setImages(){
        println("\n Taille :"+draws.size)/*
        if (draws.size==1){
            println("rtentre la")
            val mainDraw=findViewById<ImageView>(R.id.mainDraw)
            mainDraw.setImageBitmap(convertX64toImg(draws[0].image))
        }
        else if(draws.size>1){
            val mainDraw=findViewById<ImageView>(R.id.mainDraw)
            val leftDraw=findViewById<ImageView>(R.id.leftDraw)
            val rightDraw=findViewById<ImageView>(R.id.rightDraw)
        }*/
    }
}