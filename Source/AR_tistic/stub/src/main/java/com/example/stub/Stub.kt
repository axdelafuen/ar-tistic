package com.example.stub

import com.example.classlib.*
import java.util.*

class Stub : IPersistancemanager {

    public override fun saveData(users:List<User>) {
        TODO("Not yet implemented")
    }

    public override fun loadData():List<User>{
        return usr()
    }
    fun usr():List<User>{
        val usr1:User = User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), subscribers = hashMapOf(), nbReport = 0)
        return listOf(usr1)
    }
}