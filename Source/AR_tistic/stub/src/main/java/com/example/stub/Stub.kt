package com.example.stub

import com.example.classlib.*
import com.example.classlib.Collection
import java.sql.Time
import java.util.*
import kotlin.collections.HashMap

class Stub : IPersistancemanager {
    var UserHashMap=hashMapOf(
        0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), nbReport = 0 ),
        1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        2 to User(id= 2, name = "Patrick", profilePicture = "./img/pp/Fredo.jpg", email = "patrick@gmail.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        3 to User(id= 3, name = "Nina", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        4 to User(id= 4, name = "Louis", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        5 to User(id= 5, name = "1", profilePicture = "./img/pp/Fredo.jpg", email = "2", password="1", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
    )
    var IntPtsHashMap= hashMapOf(
        0 to InterestPoint(id=0,name="IUT", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
        1 to InterestPoint(id=1,name="MAISON", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
        2 to InterestPoint(id=2,name="RUE", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
    )
    var DrawsHashMap = hashMapOf(
        0 to Draw(id=0,name="Peinture bleu sur fond bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 ),
        1 to Draw(id=1,name="bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 ),
        2 to Draw(id=2,name="Peinture", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 )


    )
    override fun loadData():(com.example.classlib.Collection){
        var collec:com.example.classlib.Collection = Collection(loadUsers(),loadInterestPoints(),loadDraws())
        return collec
    }
    fun loadUsers():HashMap<Int,User>{
        return UserHashMap
    }
    fun loadInterestPoints():HashMap<Int,InterestPoint>{
        return  IntPtsHashMap
    }
    fun loadDraws():HashMap<Int,Draw>{
        return  DrawsHashMap
    }
    fun loadDataIdx(idxUser:Int, nbUser:Int,idxPt:Int, nbPt:Int,idxDraw: Int, nbDraw:Int):(com.example.classlib.Collection){
        var collec:com.example.classlib.Collection = Collection(loadUsersIndex(idxUser,nbUser),loadInterestPointsIndex(idxPt,nbPt),loadDrawsIndex(idxDraw,nbDraw))
        return collec
    }
    fun loadUsersIndex(idx:Int, nb:Int):HashMap<Int,User>{// Return of an hashmap with user in index position
        val ret = HashMap<Int, User>()// hashmap retourné avec la tranche de valeur demandé
        for ((cpt, user) in UserHashMap.values.withIndex()){
            if ((cpt >= idx) && (cpt<nb)){
                ret[cpt] = user
            }
        }
        return  ret
    }
    fun loadInterestPointsIndex(idx:Int, nb:Int):HashMap<Int,InterestPoint>{// Return of an hashmap with point of interest in index position
        val ret = HashMap<Int, InterestPoint>()// hashmap retourné avec la tranche de valeur demandé
        for ((cpt, point) in IntPtsHashMap.values.withIndex()){
            if ((cpt >= idx) && (cpt<nb)){
                ret[cpt] = point
            }
        }
        return  ret
    }
    fun loadDrawsIndex(idx:Int, nb:Int):HashMap<Int,Draw>{// Return of an hashmap with draws in index position
        val ret = HashMap<Int, Draw>()// hashmap retourné avec la tranche de valeur demandé
        for ((cpt, draw) in DrawsHashMap.values.withIndex()){
            if ((cpt >= idx) && (cpt<nb)){
                ret[cpt] = draw
            }
        }
        return  ret
    }
    //USERS FUNCTIONS
    fun getUserById(idUser:Int):User?{
        return UserHashMap[idUser]
    }
}