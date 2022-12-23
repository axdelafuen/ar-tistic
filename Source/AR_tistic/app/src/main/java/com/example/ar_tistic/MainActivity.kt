package com.example.ar_tistic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.example.classlib.Manager
import com.example.classlib.Util
import com.example.clientapi.ClientAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //Manager -> 'll be given to all activities
    val manager=Manager(ClientAPI())
    // Persistance loaded
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        checkLogPswd()
        val register=findViewById<Button>(R.id.registerButton)
        register.setOnClickListener{
            val intent = Intent(applicationContext,RegisterActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
        }
    }
    private fun checkLogPswd() {

        val connect = findViewById<Button>(R.id.LoginButton)
        val log = findViewById<EditText>(R.id.LoginEdit)
        val mdp = findViewById<EditText>(R.id.MdpEdit)
        val err = findViewById<TextView>(R.id.ErrorLog)

        connect.setOnClickListener {
            err.visibility = View.GONE
            val cttLog = log.text.toString()
            val cttMdp = mdp.text.toString()
            //Empty fields
            if (cttMdp.trim().isEmpty() || cttLog.trim().isEmpty()) {
                Toast.makeText(
                    this,
                    "l'Email ou le mot de passe ne peut etre vide",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    kotlin.runCatching {
                        //check passwrd & log
                        if (!existLogPasswd(cttLog, cttMdp)) {// non equal
                            println("DEBUG LOGIN")
                            runOnUiThread{
                                findViewById<ProgressBar>(R.id.LoginLoading).visibility = View.INVISIBLE
                                err.visibility = View.VISIBLE
                            }
                        }
                        //found log & password
                        else {
                            findViewById<ProgressBar>(R.id.LoginLoading).visibility = View.INVISIBLE
                            //Permissions localisation
                            if (Build.VERSION.SDK_INT >= 23) {
                                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    // Add user to pers once passed all verifications
                                    //manager.usr = loadUser(cttLog, cttMdp)
                                    val intent = Intent(applicationContext, MapActivity::class.java)
                                    intent.putExtra("manager", manager)
                                    startActivity(intent)
                                    //finish()
                                } else {
                                    ActivityCompat.requestPermissions(
                                        this@MainActivity,
                                        arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ),
                                        1
                                    )
                                }
                            }
                        }
                    }
                }
                runOnUiThread {
                    findViewById<ProgressBar>(R.id.LoginLoading).visibility = View.VISIBLE
                }
            }
        }
    }
    /// FUNCTION
    /// Login and mdp exists
    private fun existLogPasswd(name:String, pswd:String): Boolean {
        val res = manager.persistence.getuserByEmail(name)
        if (res != null) {
            if(name == res.email || name == res.name){
                println("FIND USER")
                if(res.password == Util.hashPassword(pswd)){
                    println("GOOD PASSWORD")
                    return true
                }
            }
        }
        return false
    }
}

