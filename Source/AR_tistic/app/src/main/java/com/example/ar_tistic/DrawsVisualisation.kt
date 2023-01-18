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
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.example.ar_tistic.MainActivity.Companion.manager
import com.example.classlib.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.math.absoluteValue

class DrawsVisualisation : AppCompatActivity() {
    lateinit var draws:ArrayList<Draw>
    lateinit var interestPoint:InterestPoint
    lateinit var draw:ArrayList<Bitmap>
    var like = false
    var idx = 0

    private val GALLERY_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draws_visualisation)
        createButtons()
        val id=0
        //interestPoint= manager.persistence.getInterestPointById(id)
        //draws=manager.persistence.getDrawsInInterestPoint(interestPoint.id)
        //buttons
        val previous = findViewById<ImageView>(R.id.previous)
        val nextt = findViewById<ImageView>(R.id.next)
        val add = findViewById<ImageView>(R.id.addDrawing)
        val report = findViewById<ImageView>(R.id.report)
        val liked = findViewById<ImageView>(R.id.liked_true)
        val like = findViewById<ImageView>(R.id.liked_false)
        liked.isVisible=false
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
        liked.setOnClickListener{
            like()
        }
        //Init fake var

        interestPoint = InterestPoint(id=0,name="IUT", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png")
        draws = ArrayList()
        draws.add(Draw(id =0,name ="Peinture bleu sur fond bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24,10,10), authors = hashMapOf(), nbView = 60, nbReport = 0 ))
        draw = ArrayList()
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
        idx --
        //idx=idx.absoluteValue
        setImages()
        return
    }
    fun nextt(){
        idx++
        setImages()
        return
    }
    fun add(){
        openGallery()
    }
    fun report(){

        return
    }
    fun like(){
        val full_heart=findViewById<ImageView>(R.id.liked_true)
        val empty_heart=findViewById<ImageView>(R.id.liked_false)

        if (!like){
            full_heart.isVisible=true
            empty_heart.isVisible=false
            like=true
        }
        else{
            full_heart.isVisible=false
            empty_heart.isVisible=true
            like=false
        }
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
            manager.usr.profilePicture=x64
            GlobalScope.launch {
                //manager.persistence.updateUser(manager.usr.id,manager.usr)
            }
            val img=convertX64toImg(x64)
            draw.add(img)
            //imageView.setImageBitmap(img)
            setImages()
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
        if (draw.size==0){
            return
        }
        if (draw.size==1){
            val mainDraw=findViewById<ImageView>(R.id.mainDraw)
            mainDraw.setImageBitmap(draw[0])
            return
        }
        else if(draw.size==2){
            val mainDraw=findViewById<ImageView>(R.id.mainDraw)
            val leftDraw=findViewById<ImageView>(R.id.leftDraw)
            val rightDraw=findViewById<ImageView>(R.id.rightDraw)
            mainDraw.setImageBitmap(draw[((idx)%2).absoluteValue])
            leftDraw.setImageBitmap(draw[((idx+1)%2).absoluteValue])
            rightDraw.setImageBitmap(draw[((idx+1)%2).absoluteValue])
            return
        }
        else{
            val mainDraw=findViewById<ImageView>(R.id.mainDraw)
            val leftDraw=findViewById<ImageView>(R.id.leftDraw)
            val rightDraw=findViewById<ImageView>(R.id.rightDraw)
            mainDraw.setImageBitmap(draw[((idx)%3).absoluteValue])
            leftDraw.setImageBitmap(draw[((idx+1)%3).absoluteValue])
            rightDraw.setImageBitmap(draw[((idx+2)%3).absoluteValue])
            return
        }
    }
}