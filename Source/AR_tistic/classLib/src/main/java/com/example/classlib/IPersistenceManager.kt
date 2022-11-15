package com.example.classlib

import User

interface IPersistenceManager : java.io.Serializable{
    var userHashMap:HashMap<Int,User>
    var intPtsHashMap:HashMap<Int,InterestPoint>
    var drawsHashMap:HashMap<Int,Draw>
    // USER METHODS
    fun loadData():(com.example.classlib.Collection)
    fun getUserById(idUser:Int):User?
    fun createUser(usr:User)
    fun updateUser(id:Int,usr:User)
    fun deleteUser(id:Int)
    fun finUserByLogPswd(log:String, psswrd:String):User

    // DRAWS METHODS

    // INTERESTPOINTS METHODS
}