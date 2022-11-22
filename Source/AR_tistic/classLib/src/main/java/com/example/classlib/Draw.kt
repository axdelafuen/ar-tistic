package com.example.classlib

import java.io.Serializable
import kotlin.collections.HashMap

class Draw(
    var id:Int,
    var name:String,
    var image:String,
    var interestPoint: HashMap<Int,InterestPoint>,
    var creationDate: Date,
    var lifeTime: Time,
    var authors:HashMap<Int,User>,
    var nbView:Int,
    var nbReport:Int):Serializable{

    init{
        this.id = id
        this.name = name
        this.image = image
        this.interestPoint = interestPoint
        this.creationDate = creationDate
        this.lifeTime = lifeTime
        this.authors = authors
        this.nbView = nbView
        this.nbReport = nbReport
    }
}
