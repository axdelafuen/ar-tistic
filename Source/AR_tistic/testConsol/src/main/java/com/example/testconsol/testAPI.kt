package com.example.testconsol

import com.example.classlib.Collection
import com.example.classlib.Date
import com.example.classlib.User
import com.example.classlibdto.UserDTO
import com.example.clientapi.ClientAPI
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

fun main(){
    println("API - Test Console : \n")
    var url = "https://codefirst.iut.uca.fr/containers/api-artistic-axelde_la_fuente/"
    var urlUserById0 = URL("http://localhost:7070/users/0/")
    var urlUserById2 = URL("http://localhost:7070/users/2/")
    var urlUserById3 = URL("http://localhost:7070/users/3/")
    var urlUserById7 = URL("http://localhost:7070/users/7/")


    val userDTO = User(id = 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", password="1233", email = "alice@alice.kt", birthDate = Date(1999, 12 ,12), subscribes = hashMapOf(), nbReport = 0 )
    val user = UserDTO(0,"API_TEST","./img/api.png","api.test@gmail.com","1234", Date(1989,10,5), hashMapOf(0 to userDTO),0 )
    val gson = Gson()
    val jsonData = gson.toJson(user)

    //println(gson.fromJson(get(URL(url+"loadData")), Collection::class.java).users.get(1)?.name)

    var api = ClientAPI()
    var u = api.getuserByEmail("alice@alice.kt")

    //println(u.name)

    //println("\n"+jsonData+"\n")

    //println(get(URL(urlAllUsers+"users/")))

/*
    println(get(urlAllUsers))
    println("//////////////")
    post(urlAllUsers,jsonData)
    println("//////////////")
    val usr = gson.fromJson(get(urlUserById0),User::class.java)
    println(usr.name+"//"+usr.nbReport)
    println("//////////////")
    val usr2 = gson.fromJson(get(urlUserById3),User::class.java)
    println(usr2.name+(usr2.subscribes.get(0)?.name))
*/
    /*
    get(urlAllUsers)
    put(urlUserById0,jsonData)

    get(urlAllUsers)

    delete(urlUserById2)

    get(urlAllUsers)
    */
}

fun get(url:URL):String{
    lateinit var jsonStr:String
    with(url.openConnection() as HttpURLConnection){
        requestMethod = "GET"

        inputStream.bufferedReader().use{
            it.lines().forEach{ line -> jsonStr = line }
        }
    }
    //println(jsonStr)
    //println("\n")
    return jsonStr
}
fun delete(url:URL){
    with(url.openConnection() as HttpURLConnection){
        requestMethod  = "DELETE"
        this.setRequestProperty("Content-Type", "text/plain")
        doInput = true
        doOutput = false

        println("Code de retour du delete (204 = succes) : "+this.responseCode)
    }
}
fun post(url:URL, data:String){
    with(url.openConnection() as HttpURLConnection){
        requestMethod = "POST"
        doOutput = true
        this.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        this.setRequestProperty("Content-Length", data.length.toString())

        val wr = OutputStreamWriter(this.outputStream)
        wr.write(data)
        wr.flush()

        BufferedReader(InputStreamReader(inputStream)).use {
            val response = StringBuffer()

            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            //println("Response : $response")
        }

    }
}
fun put(url:URL, data:String) {
    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "PUT"
        doOutput = true

        this.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        this.setRequestProperty("Content-Length", data.length.toString())

        val wr = OutputStreamWriter(outputStream)
        wr.write(data)
        wr.flush()

        BufferedReader(InputStreamReader(inputStream)).use {
            val response = StringBuffer()

            var inputLine = it.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = it.readLine()
            }
            println("Response : $response")
        }
    }
}
