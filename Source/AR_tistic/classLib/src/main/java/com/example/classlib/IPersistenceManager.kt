package com.example.classlib

import java.io.Serializable

interface IPersistenceManager : Serializable {
    // USER METHODS
    fun getUserById(idUser:Int):User?
    fun getuserByNameOrEmail(content:String):User?
    fun createUser(usr:User)
    fun updateUser(id:Int,usr:User)
    fun deleteUser(id:Int)
    fun findUserByLogPswd(log:String, psswrd:String):User

    // DRAWS METHODS

    // INTERESTPOINTS METHODS
}