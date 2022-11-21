package com.example.classlib
import java.io.Serializable;

class User(var id:Int, var name: String, var profilePicture:String="@drawable/pp_edit", var email: String, var password:String, var birthDate: Date, var subscribes:HashMap<Int,User>, var nbReport:Int): Serializable {
    init{
        this.id=id
        this.email=email
        this.profilePicture=profilePicture
        this.name=name
        this.password=password
        this.birthDate=birthDate
        this.subscribes=subscribes
        this.nbReport=nbReport
    }
}