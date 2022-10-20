package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*

class InterestPointDao{

    var data = Stub()

    fun getInterestPoint():HashMap<Int,InterestPoint>?{
        return data.intPtsHashMap
    }

    fun getInterestPointById(id:Int):InterestPoint?{
        return data.getInterestPointById(id)
    }

    fun getInterestPointWithIndex(idx:Int, nb:Int):HashMap<Int,InterestPoint>?{
        return data.loadInterestPointsIndex(idx,nb)
    }

    fun deleteInterestPoint(id:Int){
        data.deleteInterestPoint(id)
    }

    fun updateInterestPoint(id:Int, intPoint:InterestPoint){
        data.updateInterestPoint(id, intPoint)
    }

    fun createInterestPoint(intPoint: InterestPoint){
        data.createInterestPoint(intPoint)
    }


}
