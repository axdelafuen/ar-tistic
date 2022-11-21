package com.example.ar_tistic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.example.stub.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.example.classlib.Date
import com.example.classlib.Manager
import com.example.classlib.User

class MainActivity : AppCompatActivity() {
    //Main user -> init null
    var usr:User = User(0, "0", "@drawable/pp_edit","0","0", Date(0,0,0), hashMapOf(),0 )//currrent User, transfert to other views no need to reload
    //Manager -> 'll be given to all activities
    val manager=Manager(Stub(),usr)// replace by pers needed
    // Persistance loaded
    val pers = manager.persistence.loadData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        checkLogPswd()
        val register=findViewById<Button>(R.id.registerButton)
        register.setOnClickListener{
            val intent = Intent(applicationContext,RegisterActivity::class.java)
            intent.putExtra("manager", manager)
            startActivity(intent)
            finish()
        }
    }
    private fun checkLogPswd(){
        val connect = findViewById<Button>(R.id.LoginButton)
        val log = findViewById<EditText>(R.id.LoginEdit)
        val mdp = findViewById<EditText>(R.id.MdpEdit)
        val err = findViewById<TextView>(R.id.ErrorLog)
        connect.setOnClickListener {
            err.visibility= View.GONE
            val cttLog=log.text.toString()
            val cttMdp=mdp.text.toString()
            //Empty fields
            if (cttMdp.trim().isEmpty()||cttLog.trim().isEmpty()){
                Toast.makeText(this,"l'Email ou le mot de passe ne peut etre vide", Toast.LENGTH_LONG).show()
            }
            else{
                //check passwrd & log
                if(!existLogPasswd(cttLog,cttMdp)){// non equal
                    err.visibility= View.VISIBLE
                }
                //found log & password
                else{
                    //Permissions localisation
                    if (Build.VERSION.SDK_INT >= 23) {
                        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        {
                            // Add user to pers once passed all verifications
                            manager.usr=loadUser(cttLog,cttMdp)
                            val intent = Intent(applicationContext, MapActivity::class.java)
                            intent.putExtra("manager", manager)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
                        }
                        }
                    }
                }
            }
        }

    /// FUNCTION
    /// Login and mdp exists
    private fun existLogPasswd(name:String, pswd:String):Boolean{
        val users= pers.users
        for (user in users.values){
            if((user.name==name&&pswd==user.password) || (user.email==name&&pswd==user.password)){
                usr=user
                return true
            }
        }
        return false
    }
    /// FUNCTION
    /// Load by the persistance by log & passwrd
    private fun loadUser(log:String, pswd:String):User{// add user to the pers when
        return manager.persistence.findUserByLogPswd(log,pswd)
    }
}
