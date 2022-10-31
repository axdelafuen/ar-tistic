package com.example.classlib

class InterestPoint {
    val id: Int
    val name: String
    val desc: String
    val latitude: Double
    val longitude: Double
    val picture: String

    constructor(id:Int, name:String, desc:String, latitude:Double, longitude:Double, picture:String){
        this.id = id
        this.name = name
        this.desc = desc
        this.latitude = latitude
        this.longitude = longitude
        this.picture = picture
    }
}
