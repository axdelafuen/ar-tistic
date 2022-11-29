package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.classlib.*
import com.example.stub.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RegisterActivity: AppCompatActivity() {
    lateinit var manager:Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        manager=intent.getSerializableExtra("manager") as Manager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val returnMain=findViewById<Button>(R.id.returnLogPageButton)
        val register=findViewById<Button>(R.id.registerB)
        returnMain.setOnClickListener{
            finish()
        }

        register.setOnClickListener{
            check()
        }
    }
    fun check(){//check errors when register button is press

        val email = findViewById<EditText>(R.id.email)
        val cttmail=email.text.toString()
        val errMail = findViewById<TextView>(R.id.emailused)
        val errPswd = findViewById<TextView>(R.id.unequalPswd)
        val pswd1 = findViewById<EditText>(R.id.psswd)
        val cttPswd1=pswd1.text.toString()
        val pswd2 = findViewById<EditText>(R.id.confirmPsswd)
        val cttPswd2=pswd2.text.toString()
        errMail.visibility= View.INVISIBLE
        errPswd.visibility= View.INVISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                if(checkEmail(cttmail)){//used email
                    println("DEBUG REGISTER")
                    errMail.visibility= View.VISIBLE
                }

                else {
                    if (cttmail.trim().isEmpty() || cttPswd1.trim().isEmpty() || cttPswd2.trim().isEmpty()) {//empty fields
                        Toast.makeText(this@RegisterActivity,"l'Email ou le mot de passe ne peut etre vide", Toast.LENGTH_LONG).show()
                    }

                    else {//unused mail
                        CoroutineScope(Dispatchers.IO).launch {
                            runCatching {
                                if (checkPswd()) {//similar password
                                    val usr1: User = createUser(cttmail, cttPswd1)
                                    manager.persistence.createUser(usr1)
                                    //Test -> creation of user
                                    val intent = Intent(applicationContext, MapActivity::class.java)
                                    intent.putExtra("manager", manager)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    errPswd.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                        runOnUiThread{
                            ///
                        }
                    }
                }
            }

    }
    fun checkPswd():Boolean{//return true if the 2 password are equal
        val pswd1 = findViewById<EditText>(R.id.psswd)
        val cttPswd1=pswd1.text.toString()
        val pswd2 = findViewById<EditText>(R.id.confirmPsswd)
        val cttPswd2=pswd2.text.toString()
        return cttPswd1.equals(cttPswd2)
    }
    fun checkEmail(email:String):Boolean{//return true if email is already used
        val res = manager.persistence.getuserByEmail(email)?:return false
        return true
    }
    fun createUser(email:String, pswd:String):User{// return new user with uniq id and the email and mdp giv in parameter
        var id=1//Id from manager
        val ppDefault="/img/ppDefault"
        return User(id, "User"+id, "",email,pswd, Date(1999,2,2),hashMapOf(),0)
    }
}