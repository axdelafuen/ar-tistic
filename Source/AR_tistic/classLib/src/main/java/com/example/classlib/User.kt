package com.example.classlib

import java.util.Date


data class User(
    val id: Int,
    val name: String,
    val profilePicture:String,
    val email: String,
    val password:String,
    val birthDate: Date,
    val subscribes:HashMap<Int,User>,
    val nbReport:Int)
