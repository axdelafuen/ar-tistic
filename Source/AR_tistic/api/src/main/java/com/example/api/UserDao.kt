package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*

class UserDao {

    var data = Stub()

    fun getUsers():HashMap<Int,User>?{
        return data.userHashMap
    }

    fun getUserById(id:Int):User?{
        return data.getUserById(id)
    }

    fun getUserWithIndex(idx:Int, nb:Int):HashMap<Int,User>?{
        return data.loadUsersIndex(idx,nb)
    }

    fun deleteUser(id:Int){
        data.deleteUser(id)
    }

    fun updateUser(id:Int, user:User){
        data.updateUser(id, user)
    }

    fun createUser(user:User){
        data.createUser(user)
    }
}
