package com.example.clientapi

import com.example.classlib.*
import com.example.classlib.Collection
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Suppress("NewApi")
class ClientAPI:IPersistenceManager,java.io.Serializable{

    val url = "https://codefirst.iut.uca.fr/containers/api-artistic-axelde_la_fuente/" // for release
    //val url = "http://localhost:1705/" //for locals

    fun loadData(): Collection? {
        try{
            return Gson().fromJson(get(URL(url+"loadData")),Collection::class.java)
        }catch(e:IOException){
            return null
        }
    }

    override fun getUserById(idUser: Int): User? {
        try{
            return Gson().fromJson(get(URL(url+"users/"+idUser.toString())),User::class.java)
        }catch(e: IOException){
            return null
        }
    }

    override fun getuserByEmail(content: String): User? {
        try{
            return Gson().fromJson(get(URL(url+"users/email/"+content)),User::class.java)
        }catch(e:IOException){
            return null
        }
    }

    override fun createUser(usr: User) {
        post(URL(url+"users"),Gson().toJson(usr))
    }

    override fun updateUser(id: Int, usr: User) {
        put(URL(url+"users"+id as String),Gson().toJson(usr))
    }

    override fun deleteUser(id: Int) {
        delete(URL(url+"users"+id as String))
    }

    override fun findUserByLogPswd(log: String, psswrd: String): User? {
        try{
            return Gson().fromJson(get(URL(url+"user/pwd/"+log+"/"+psswrd+"/")),User::class.java)
        }catch(e:IOException){
            return null
        }
    }

    /// http requests :

    @Suppress("NewApi")
    private fun get(url: URL):String{
        lateinit var jsonStr: String
        with(url.openConnection() as HttpURLConnection){
            requestMethod = "GET"
            inputStream.bufferedReader().use {
                it.lines().forEach { line -> jsonStr = line }
            }
        }
        return jsonStr
    }
    private fun delete(url: URL){
        with(url.openConnection() as HttpURLConnection){
            requestMethod  = "DELETE"
            this.setRequestProperty("Content-Type", "text/plain")
            doInput = true
            doOutput = false

            println("Code de retour du delete (204 = succes) : "+this.responseCode)
        }
    }
    private fun post(url: URL, data:String){
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
    private fun put(url: URL, data:String) {
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

}