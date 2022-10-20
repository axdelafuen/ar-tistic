package com.example.api

import com.example.classlib.Date
import com.example.classlib.User
import com.google.gson.Gson
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

fun main(){
    println("API - Test Console : \n")
    var urlAllUsers = URL("http://localhost:7070/users/")
    var urlUserById0 = URL("http://localhost:7070/users/0/")
    var urlUserById2 = URL("http://localhost:7070/users/2/")
    var urlUserById3 = URL("http://localhost:7070/users/3/")

    get(urlAllUsers)

    val user = User(0,"API_TEST","./img/api.png","api.test@gmail.com","1234", Date(1989,10,5), hashMapOf(),0 )
    val gson = Gson()
    val jsonData = gson.toJson(user)
    println("\n"+jsonData+"\n")

    post(urlAllUsers,jsonData)

    get(urlAllUsers)
/*
    put(urlUserById0,jsonData)

    get(urlAllUsers)

    delete(urlUserById2)

    get(urlAllUsers)

 */
}

fun get(url:URL){
    with(url.openConnection() as HttpURLConnection){
        requestMethod = "GET"

        inputStream.bufferedReader().use{
            it.lines().forEach{ line -> println(line)}
        }
    }
    println("\n")
}
fun delete(url:URL){
    with(url.openConnection() as HttpURLConnection){
        requestMethod  = "DELETE"
        this.setRequestProperty("Content-Type", "text/plain")
        doInput = true
        doOutput = false

        println("Code de retour du delete (204 = succes) : "+this.responseCode);
    }
}
fun post(url:URL, data:String){
    with(url.openConnection() as HttpURLConnection){
        requestMethod = "POST"
        doInput = false
        doOutput = true
        this.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        this.setRequestProperty("Accept", "application/x-www-form-urlencoded")

        val wr = OutputStreamWriter(this.outputStream)
        wr.write(data)
        wr.flush()
    }
}
fun put(url:URL, data:String){
    with(url.openConnection() as HttpURLConnection){
        requestMethod = "PUT"
        doOutput = true
        val wr = OutputStreamWriter(outputStream)
        wr.write(data)
        wr.flush()
    }
}