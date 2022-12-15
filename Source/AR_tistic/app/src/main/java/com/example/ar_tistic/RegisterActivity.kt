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
    lateinit var manager: Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        manager = intent.getSerializableExtra("manager") as Manager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val returnMain = findViewById<Button>(R.id.returnLogPageButton)
        val register = findViewById<Button>(R.id.registerB)
        returnMain.setOnClickListener {
            finish()
        }

        register.setOnClickListener {
            check(manager.persistence)
        }
    }

    fun check(pers: IPersistenceManager) {//check errors when register button is press
        //variables
        val email = findViewById<EditText>(R.id.email)
        val pswd1 = findViewById<EditText>(R.id.psswd)
        val pswd2 = findViewById<EditText>(R.id.confirmPsswd)

        val cttmail = email.text.toString()
        val cttPswd1 = pswd1.text.toString()
        val cttPswd2 = pswd2.text.toString()

        var errMail = findViewById<TextView>(R.id.emailused)
        var errPswd = findViewById<TextView>(R.id.unequalPswd)

        errMail.visibility = View.INVISIBLE
        errPswd.visibility = View.INVISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                if (checkEmail(cttmail)) {//used email
                    println("DEBUG REGISTER")
                    errMail.visibility = View.VISIBLE
                } else {
                    println("DEBUG REGISTER")
                    if (cttmail.trim().isEmpty() || cttPswd1.trim().isEmpty() || cttPswd2.trim()
                            .isEmpty()
                    ) {//empty fields
                        Toast.makeText(
                            this@RegisterActivity,
                            "l'Email ou le mot de passe ne peut etre vide",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {//unused mail
                        CoroutineScope(Dispatchers.IO).launch {
                            runCatching {
                                if (checkEmail(cttmail)) {//used email
                                    runOnUiThread{
                                        errMail.visibility = View.VISIBLE
                                    }
                                } else {//unused mail
                                    if (checkPswd()) {//similar password
                                        val usr1: User = createUser(cttmail, cttPswd1)
                                        pers.createUser(usr1)
                                        val intent = Intent(applicationContext, MapActivity::class.java)
                                        intent.putExtra("manager", manager)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        runOnUiThread{
                                            errPswd.visibility = View.VISIBLE
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    fun checkPswd(): Boolean {//return true if the 2 password are equal
        val pswd1 = findViewById<EditText>(R.id.psswd)
        val pswd2 = findViewById<EditText>(R.id.confirmPsswd)
        val cttPswd1 = pswd1.text.toString()
        val cttPswd2 = pswd2.text.toString()

        return cttPswd1.equals(cttPswd2)
    }

    fun checkEmail(email: String): Boolean {//return true if email is already used
        manager.persistence.getuserByEmail(email) ?: return false
        return true
    }

    fun createUser(
        email: String,
        pswd: String
    ): User {// return new user with uniq id and the email and mdp giv in parameter
        val ppDefault = "/img/ppDefault"
        return User(1, "Username", "", email, Util.hashPassword(pswd), Date(1999, 2, 2), hashMapOf(), 0)
    }
}