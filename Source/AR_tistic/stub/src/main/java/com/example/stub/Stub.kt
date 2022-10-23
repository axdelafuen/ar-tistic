package com.example.stub

import com.example.classlib.*
import com.example.classlib.Collection
import java.sql.Time
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.HashMap

class Stub : IPersistancemanager{
    var userHashMap=loadData().users
        get() {
            return field
        }
        set(value) {field=value}
    var intPtsHashMap=loadData().interestPoints
        get() {return  field}
        set(value) {field=value}
    var drawsHashMap=loadData().draws
        get() {
            return field
        }
        set(value) {field=value}
    var lastId:AtomicInteger= AtomicInteger(userHashMap.size-1)
    override fun loadData():(com.example.classlib.Collection){
        var collec:com.example.classlib.Collection = Collection(loadUsers(),loadInterestPoints(),loadDraws())
        return collec
    }
    fun loadUsers():HashMap<Int,User>{

        return hashMapOf(
            0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), nbReport = 0 ),
            1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
            2 to User(id= 2, name = "Patrick", profilePicture = "./img/pp/Fredo.jpg", email = "patrick@gmail.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
            3 to User(id= 3, name = "Nina", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
            4 to User(id= 4, name = "Louis", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
            5 to User(id= 5, name = "1", profilePicture = "./img/pp/Fredo.jpg", email = "2", password="1", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        )
    }
    fun loadInterestPoints():HashMap<Int,InterestPoint>{
        return  hashMapOf(
            0 to InterestPoint(id=0,name="IUT", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
            1 to InterestPoint(id=1,name="MAISON", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
            2 to InterestPoint(id=2,name="RUE", desc="IUT de Clermont", latitude=1.0000, longitude=2.00000, picture="./img/intPoint/IUT.png"),
        )
    }
    fun loadDraws():HashMap<Int,Draw>{
        return hashMapOf(
            0 to Draw(id=0,name="Peinture bleu sur fond bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 ),
            1 to Draw(id=1,name="bleu", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 ),
            2 to Draw(id=2,name="Peinture", image = "./img/draw/0.jpg", interestPoint = hashMapOf(), creationDate = Date(1999,2,2), lifeTime = Time(24), authors = hashMapOf(), nbView = 60, nbReport = 0 )
        )
    }
    fun loadDataIdx(idxUser:Int, nbUser:Int,idxPt:Int, nbPt:Int,idxDraw: Int, nbDraw:Int):(com.example.classlib.Collection){
        var collec:com.example.classlib.Collection = Collection(loadUsersIndex(idxUser,nbUser),loadInterestPointsIndex(idxPt,nbPt),loadDrawsIndex(idxDraw,nbDraw))
        return collec
    }
    fun loadUsersIndex(idx:Int, nb:Int):HashMap<Int,User>{// Return of an hashmap with user in index position
        if(idx+nb>userHashMap.size)return hashMapOf()//test si idx > nb user
        val ret = HashMap<Int, User>()// hashmap retourné avec la tranche de valeur demandé
        var cpt=0
        while(cpt<nb){
            ret[cpt]=userHashMap.values.elementAt(cpt+idx)
            cpt++
        }
        return  ret
    }
    fun loadInterestPointsIndex(idx:Int, nb:Int):HashMap<Int,InterestPoint>{// Return of an hashmap with point of interest in index position
        if(idx+nb>intPtsHashMap.size)return hashMapOf()//test si idx > nb user
        val ret = HashMap<Int, InterestPoint>()// hashmap retourné avec la tranche de valeur demandé
        var cpt=0
        while(cpt<nb){
            ret[cpt]=intPtsHashMap.values.elementAt(cpt+idx)
            cpt++
        }
        return  ret
    }
    fun loadDrawsIndex(idx:Int, nb:Int):HashMap<Int,Draw>{// Return of an hashmap with draws in index position
        if(idx+nb>drawsHashMap.size)return hashMapOf()//test si idx > nb user
        val ret = HashMap<Int, Draw>()// hashmap retourné avec la tranche de valeur demandé
        var cpt=0
        while(cpt<nb){
            ret[cpt]=drawsHashMap.values.elementAt(cpt+idx)
            cpt++
        }
        return  ret
    }



    //USERS FUNCTIONS
    fun getUserById(idUser:Int):User?{
        return userHashMap[idUser]
    }
    fun createUser(usr:User){// créé un nouveau
        val id=lastId.incrementAndGet()
        userHashMap.put(id,User(id,usr.name, usr.profilePicture, usr.email, usr.password, usr.birthDate, usr.subscribes, usr.nbReport))
    }
    fun updateUser(id:Int,usr:User){// modify
        userHashMap.put(id,User(id,usr.name, usr.profilePicture, usr.email, usr.password, usr.birthDate, usr.subscribes, usr.nbReport))
    }
    fun deleteUser(id:Int){
        userHashMap.remove(id)
    }

    //INTEREST POINT FUNCTIONS
    fun getInterestPointById(idPt:Int):InterestPoint?{
        return intPtsHashMap[idPt]
    }
    fun createInterestPoint(intPt:InterestPoint){// créé un nouveau
        val id=lastId.incrementAndGet()
        intPtsHashMap.put(id, InterestPoint(id,intPt.name,intPt.desc,intPt.latitude,intPt.longitude,intPt.picture))
    }
    fun updateInterestPoint(id:Int,intPt:InterestPoint){// modify
        intPtsHashMap.put(id, InterestPoint(id,intPt.name,intPt.desc,intPt.latitude,intPt.longitude,intPt.picture))
    }
    fun deleteInterestPoint(id:Int){
        intPtsHashMap.remove(id)
    }

    //DRAWS FUNCTIONS
    fun getDrawById(idDraw:Int):Draw?{
        return drawsHashMap[idDraw]
    }
    fun createDraw(drw:Draw){// créé un nouveau
        val id=lastId.incrementAndGet()
        drawsHashMap.put(id,Draw(id,drw.name,drw.image,drw.interestPoint,drw.creationDate,drw.lifeTime,drw.authors,drw.nbView,drw.nbReport))
    }
    fun updateDraw(id:Int,drw:Draw){// modify
        drawsHashMap.put(id,Draw(id,drw.name,drw.image,drw.interestPoint,drw.creationDate,drw.lifeTime,drw.authors,drw.nbView,drw.nbReport))
    }
    fun deleteDraw(id:Int){
        drawsHashMap.remove(id)
    }

}