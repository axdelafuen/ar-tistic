package com.example.ar_tistic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import org.osmdroid.views.MapView
import java.util.concurrent.TimeUnit

class MainPageActivity : AppCompatActivity() {
    private lateinit var lunch_button:Button
    private lateinit var debug_mode:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        fun permission_test():Int{
            if (Build.VERSION.SDK_INT >= 23) {

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), 1
                    )
                    return 1
                }
                else
                {
                    return 0
                }
            }
            return 1
        }

        fun lunch_app(){
            val intent = Intent(applicationContext,MainActivity::class.java)
            if(permission_test()==0){
                startActivity(intent)
                finish()
            }
        }
        fun debug(){
            val intent = Intent(applicationContext,DebugActivity::class.java)
            if(permission_test()==0){
                startActivity(intent)
            }
        }

        lunch_button = findViewById<Button>(R.id.lunch_button)
        lunch_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                lunch_app()
            }
        })

        debug_mode = findViewById<Button>(R.id.debug_mode)
        debug_mode.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                debug()
            }
        })
    }
}