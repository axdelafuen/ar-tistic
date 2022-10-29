package com.example.classlib

interface IPersistenceManager : java.io.Serializable{
    var userHashMap:HashMap<Int,User>
    var intPtsHashMap:HashMap<Int,InterestPoint>
    var drawsHashMap:HashMap<Int,Draw>
    fun loadData():(com.example.classlib.Collection)
    fun getUserById(idUser:Int):User?
    fun createUser(usr:User)
    fun updateUser(id:Int,usr:User)
    fun deleteUser(id:Int)
}