package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*
import java.sql.Time

class DrawDao{


    var draws = Stub().loadDraws()

    var lastId : AtomicInteger = AtomicInteger(draws.size - 1)

    fun save(id:Int, name:String, image:String, interestPoint: HashMap<Int,InterestPoint>, creationDate: Date, lifeTime: Time, authors:HashMap<Int,User>, nbView:Int, nbReport:Int ){
        val id = lastId.incrementAndGet()
        draws.put(id,Draw(id=id,name=name,image=image,interestPoint=interestPoint,creationDate=creationDate,lifeTime=lifeTime,authors=authors,nbView=nbView,nbReport=nbReport))
    }

    fun findById(id:Int):Draw?{
        return draws[id]
    }
}