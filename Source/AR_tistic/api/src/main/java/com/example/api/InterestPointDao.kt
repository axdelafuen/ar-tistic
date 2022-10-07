package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*

class InterestPointDao{

    var intPoints = Stub().loadInterestPoints()

    var lastId : AtomicInteger = AtomicInteger(intPoints.size - 1)

    fun save(name: String, desc: String, latitude: Double, longitude: Double, picture: String) {
        val id = lastId.incrementAndGet()
        intPoints.put(id, InterestPoint(id = id, name = name, desc=desc, latitude=latitude, longitude=longitude, picture=picture))
    }

    fun findById(id : Int):InterestPoint?{
        return intPoints[id]
    }
}
