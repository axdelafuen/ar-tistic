package com.example.ar_tistic

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
import com.example.ar_tistic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        checkLogPswd()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
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