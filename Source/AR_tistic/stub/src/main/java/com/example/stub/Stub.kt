package com.example.stub

import com.example.classlib.*
import java.util.*

class Stub : IPersistancemanager {

    override fun loadUsers():HashMap<Int,User>{
        return usr()
    }
    fun usr():HashMap<Int, User>{
        return hashMapOf(
            0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), nbReport = 0 ),
            1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        )
    }
}