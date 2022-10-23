package com.example.ar_tistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.classlib.*
import com.example.stub.*

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val returnMain=findViewById<Button>(R.id.returnLogPageButton)
        val register=findViewById<Button>(R.id.registerB)
        returnMain.setOnClickListener{
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        var pers = Stub() // A terme faire un manager
        register.setOnClickListener{
            check(pers)
        }
    }
    fun check(pers:IPersistance){//check errors when register button is press

        val email = findViewById<EditText>(R.id.email)
        val cttmail=email.text.toString()
        val errMail = findViewById<TextView>(R.id.emailused)
        val errPswd = findViewById<TextView>(R.id.unequalPswd)
        val pswd1 = findViewById<EditText>(R.id.psswd)
        val cttPswd1=pswd1.text.toString()
        errMail.visibility= View.INVISIBLE
        errPswd.visibility= View.INVISIBLE

        val users= pers.loadData().users

        if(checkEmail(users,cttmail)){//used email
            errMail.visibility= View.VISIBLE
        }
        else{//unused mail
            if(checkPswd()){//similar password
                pers.createUser(User(0,"","",cttmail,cttPswd1, Date(1999,2,2),
                    subscribes = hashMapOf(),
                    nbReport = 0))
                //Test -> creation of user
                println("----------Test ajout----------")
                for(usr in pers.userHashMap){
                    println(usr.value.name)
                }
                //
                val intent = Intent(applicationContext,ProfilActivity::class.java)
                intent.putExtra("email", cttmail)
                intent.putExtra("pswd", cttPswd1)
                startActivity(intent)
                finish()
            }
            else{
                errPswd.visibility= View.VISIBLE
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
    fun checkEmail(users:HashMap<Int,User>,email:String):Boolean{//return true if email is already used
        for (usr in users.values) {//check if email already used
            if(usr.email==email)return true
        }
        return false
    }
}