package com.example.myapplication

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkLogPswd()
        //Axel
    }
    val stub = Stub()
    private fun checkLogPswd(){
        val connect = findViewById<Button>(R.id.LoginButton)
        val log = findViewById<EditText>(R.id.LoginEdit)
        val mdp = findViewById<EditText>(R.id.MdpEdit)
        val err = findViewById<TextView>(R.id.ErrorLog)
        connect.setOnClickListener {
            err.visibility=View.GONE
            val cttLog=log.text.toString()
            val cttMdp=mdp.text.toString()
            if (cttMdp.trim().isEmpty()||cttLog.trim().isEmpty()){
                Toast.makeText(this,"l'Email ou mot de passe ne peut etre vide",Toast.LENGTH_LONG).show()
            }
            else{
                if(!existLogPasswd(cttLog,cttMdp)){//log et mdp pas cohérents:
                    err.visibility=View.VISIBLE
                }
                else{
                    setContentView(R.layout.activity_main)
                }
            }
        }
    }
    private fun existLogPasswd(name:String, pswd:String):Boolean{
        val users= stub.loadData()
        for (user in users){
            if(user.name==name&&pswd==user.password||user.email==name&&pswd==user.password){
                return true
            }
        }
        return false
    }
}