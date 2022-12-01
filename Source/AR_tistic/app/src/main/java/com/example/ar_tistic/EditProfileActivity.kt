package com.example.ar_tistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classlib.Manager

class EditProfileActivity : AppCompatActivity() {
    lateinit var manager: Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        manager = intent.getSerializableExtra("manager") as Manager

    }
}