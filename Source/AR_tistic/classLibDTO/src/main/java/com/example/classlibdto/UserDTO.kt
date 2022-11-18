package com.example.classlibdto

import com.example.classlib.*


data class UserDTO (
    val id: Int,
    val name: String,
    val password :String,
    val profilePicture:String,
    val email: String,
    val birthDate: Date,
    val subscribes:HashMap<Int, User>,
    val nbReport:Int
        )