package com.example.classlibdto


data class UserDTO (
    val id: Int,
    val name: String,
    val profilePicture:String,
    val email: String,
    val birthDate: DateDTO,
    val nbReport:Int
        )