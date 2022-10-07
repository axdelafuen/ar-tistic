package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*

class UserDao {

    var users = Stub().loadUsers()

    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, profilePicture:String, email: String, password:String, birthDate:Date, subscribes:HashMap<Int, User>, nbReport:Int ) {
        val id = lastId.incrementAndGet()
        users.put(id, User(id = id, name = name,  profilePicture=profilePicture, email=email, password=password, birthDate=birthDate, subscribes=subscribes, nbReport=nbReport))
    }

    fun findById(id: Int): User? {
        return users[id]
    }

    fun update(id: Int, user: User) {
        users.put(id, User(id = id, name = user.name,  profilePicture=user.profilePicture, email=user.email, password=user.password, birthDate=user.birthDate, subscribes=user.subscribes, nbReport=user.nbReport))
    }

    fun delete(id: Int) {
        users.remove(id)
    }

}
