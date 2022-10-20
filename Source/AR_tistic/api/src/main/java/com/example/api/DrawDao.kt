package com.example.api

import java.util.concurrent.atomic.AtomicInteger
import java.util.*
import kotlin.collections.HashMap
import com.example.classlib.*
import com.example.stub.*
import java.sql.Time

class DrawDao{

    var data = Stub()

    fun getDraws(): HashMap<Int, Draw>? {
        return data.drawsHashMap
    }

    fun getDrawsById(id:Int):Draw?{
        return data.getDrawById(id)
    }

    fun getDrawWithIndex(idx:Int,nb:Int):HashMap<Int,Draw>{
        return data.loadDrawsIndex(idx,nb)
    }

    fun deleteDraw(id:Int){
        data.deleteDraw(id)
    }

    fun updateDraw(id:Int,draw: Draw){
        data.updateDraw(id,draw)
    }

    fun createDraw(draw: Draw){
        data.createDraw(draw)
    }


}