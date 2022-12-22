package com.example.ar_tistic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classlib.Manager
import com.example.classlib.User
import com.example.clientapi.ClientAPI
import kotlinx.coroutines.*

class SearchActivity: AppCompatActivity() {
    lateinit var manager:Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_users)
        manager = intent.getSerializableExtra("manager") as Manager
        createButtons()
    }
    fun createButtons(){
        val back = findViewById<ImageButton>(R.id.returnMapPage)
        back.setOnClickListener {
            finish()
        }
        val submitSearch = findViewById<ImageButton>(R.id.searchSubmit)
        submitSearch.setOnClickListener{
            if(findViewById<EditText>(R.id.searchInput).text.isEmpty()){
                return@setOnClickListener
            }
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(findViewById<EditText>(R.id.searchInput).windowToken, 0)

            val recycler = findViewById<RecyclerView>(R.id.recyclerViewSearch)
            recycler.layoutManager = LinearLayoutManager(this)

            GlobalScope.launch {
                val data = searchUsers(findViewById<EditText>(R.id.searchInput).text.toString()).await()
                runOnUiThread {
                    val adapter = UserAdapter(data)
                    recycler.adapter = adapter
                    findViewById<TextView>(R.id.loading).visibility = View.INVISIBLE
                }
            }

        }

    }

    suspend fun searchUsers(pattern:String)= GlobalScope.async{
        runOnUiThread{
            findViewById<TextView>(R.id.loading).visibility = View.VISIBLE
        }
        val data = ArrayList<User>()
        //var users =  manager.persistence.patternRecognitionUsers(pattern)
        val api = ClientAPI() // temp => waiting for database request working
        //var users = api.loadData()?.users
        var users = api.patternRecognitionUsers(pattern)
        if (users != null) {
            for(user in users){
                println(user.name)
                //data.add(user.value)
            }
        }
        return@async data
    }
}

