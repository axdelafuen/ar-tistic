package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash_screen)

        val runnable = Runnable(){
            val intent = Intent(applicationContext,MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        Handler().postDelayed(runnable,2000)
    }
}