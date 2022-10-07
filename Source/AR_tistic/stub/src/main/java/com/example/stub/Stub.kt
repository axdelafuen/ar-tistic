package com.example.stub

import com.example.classlib.*
import com.example.classlib.Collection
import java.sql.Time
import java.util.*
import kotlin.collections.HashMap

class Stub : IPersistancemanager {

    override fun loadData():(com.example.classlib.Collection){
        var collec:com.example.classlib.Collection = Collection(loadUsers(),loadInterestPoints(),loadDraws())
        return collec
    }

    fun loadUsers():HashMap<Int,User>{
        return hashMapOf(
            0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), nbReport = 0 ),
            1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 )
        )
    }
    fun loadInterestPoints():HashMap<Int,InterestPoint>{
        return hashMapOf(
            0 to InterestPoint(id=0,name="IUT", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png")
        )
    }
    fun loadDraws():HashMap<Int,Draw>{
        return hashMapOf(
            0 to Draw(id=0,name="Peinture bleu sur fond bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 )
        )
    }
}