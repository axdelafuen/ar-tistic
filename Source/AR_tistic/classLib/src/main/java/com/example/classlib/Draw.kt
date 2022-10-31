package com.example.classlib

import java.sql.Time
import kotlin.collections.HashMap

class Draw{
    val id:Int
    val name:String
    val image:String
    val interestPoint: HashMap<Int,InterestPoint>
    val creationDate: Date
    val lifeTime: Time
    val authors:HashMap<Int,User>
    val nbView:Int
    val nbReport:Int

    constructor(id:Int, name:String, image:String, interestPoint: HashMap<Int,InterestPoint>, creationDate:Date, lifeTime: Time, authors:HashMap<Int,User>, nbView:Int, nbReport:Int){
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
