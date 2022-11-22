package com.example.classlib

import java.io.Serializable

interface IPersistenceManager : Serializable {
    // USER METHODS
    fun loadData():(com.example.classlib.Collection)
    fun getUserById(idUser:Int):User?
    fun createUser(usr:User)
    fun updateUser(id:Int,usr:User)
    fun deleteUser(id:Int)
    fun findUserByLogPswd(log:String, psswrd:String):User

    // DRAWS METHODS

    // INTERESTPOINTS METHODS
}