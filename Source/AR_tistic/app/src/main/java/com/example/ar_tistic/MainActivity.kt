package com.example.ar_tistic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.example.classlib.*
import com.example.stub.*
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ar_tistic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkLogPswd()
    }

    val stub = Stub()
    private fun checkLogPswd(){
        val connect = findViewById<Button>(R.id.LoginButton)
        val log = findViewById<EditText>(R.id.LoginEdit)
        val mdp = findViewById<EditText>(R.id.MdpEdit)
        val err = findViewById<TextView>(R.id.ErrorLog)
        connect.setOnClickListener {
            err.visibility= View.GONE
            val cttLog=log.text.toString()
            val cttMdp=mdp.text.toString()
            if (cttMdp.trim().isEmpty()||cttLog.trim().isEmpty()){
                Toast.makeText(this,"l'Email ou mot de passe ne peut etre vide", Toast.LENGTH_LONG).show()
            }
            else{
                if(!existLogPasswd(cttLog,cttMdp)){//log et mdp pas cohérents:
                    err.visibility= View.VISIBLE
                }
                else{
                    if(permission_test()==0){
                        val intent = Intent(applicationContext,MapActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
    private fun existLogPasswd(name:String, pswd:String):Boolean{
        val users= stub.loadUsers()
        for (user in users.values){
            if((user.name==name&&pswd==user.password) || (user.email==name&&pswd==user.password)){
                return true
            }
        }
        return false
    }
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

}