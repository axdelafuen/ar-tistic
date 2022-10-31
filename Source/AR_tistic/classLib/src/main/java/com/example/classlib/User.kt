package com.example.classlib

class User{
    val id: Int
    val name: String
    val profilePicture:String
    val email: String
    val password:String
    val birthDate: Date
    val subscribes:HashMap<Int,User>
    val nbReport:Int

    constructor(id:Int, name:String, profilePicture:String, email:String, password:String, birthDate:Date, subscribes:HashMap<Int,User>, nbReport:Int){
        this.id = id
        this.name = name
        this.profilePicture = profilePicture
        this.email = email
        this.password = password
        this.birthDate = birthDate
        this.subscribes = subscribes
        this.nbReport = nbReport
    }
}
