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
    fun patternRecognitionUsers(pattern: String): HashMap<Int,com.example.classlib.User>
    // DRAWS METHODS
    fun getDrawById(idDraw: Int): com.example.classlib.Draw?
    fun deleteDraw(idDraw: Int)
    fun updateDraw(d: com.example.classlib.Draw)
    fun getCollaborated(idDraw: Int): HashMap<Int,com.example.classlib.User>
    fun createDraw(draw: com.example.classlib.Draw)
    // INTERESTPOINTS METHODS
}