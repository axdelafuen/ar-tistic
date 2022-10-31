package com.example.classlibdto

import com.example.classlib.*

data class InterestPointDTO(
    val id: Int,
    val name : String,
    val desc : String,
    val latitude : Double,
    val longitude : Double,
    val picture : String
)