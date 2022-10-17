package com.example.ar_tistic

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.classlib.*
import com.example.stub.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val register=findViewById<Button>(R.id.registerB)
        register.setOnClickListener{saveUser()}
    }
    fun saveUser(){

    }
}