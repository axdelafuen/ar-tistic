package com.example.classlib

import java.io.Serializable;
import java.math.BigInteger
import java.security.MessageDigest

class User(var id:Int, var name: String, var profilePicture:String="@drawable/pp_edit", var email: String, var password:String, var birthDate: Date, var subscribes:HashMap<Int,User>, var nbReport:Int): Serializable {
    init{
        this.id=id
        this.email=email
        this.profilePicture=profilePicture
        this.name=name
        this.password=this.hashPassword(password)
        this.birthDate=birthDate
        this.subscribes=subscribes
        this.nbReport=nbReport
    }
    private fun hashPassword(password:String):String{
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }
}