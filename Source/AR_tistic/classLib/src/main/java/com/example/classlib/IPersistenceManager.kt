package com.example.classlib

import java.io.Serializable

interface IPersistenceManager : Serializable {
    // USER METHODS
    fun getUserById(idUser:Int):User?
    fun getuserByEmail(content:String):User?
    fun createUser(usr:User)
    fun updateUser(id:Int,usr:User)
    fun deleteUser(id:Int)
    fun findUserByLogPswd(log:String, psswrd:String):User?
    fun getLikes(id:Int):Int
    fun getFollowers(id:Int):Int
    // DRAWS METHODS

    // INTERESTPOINTS METHODS
}