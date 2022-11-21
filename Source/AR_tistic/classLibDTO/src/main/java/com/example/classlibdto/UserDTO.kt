package com.example.classlibdto

import com.example.classlib.*


data class UserDTO (
    val id: Int,
    val email: String,
    val profilePicture:String,
    val name: String,
    val password :String,
    val birthDate: Date,
    val subscribes:HashMap<Int, User>,
    val nbReport:Int
        )