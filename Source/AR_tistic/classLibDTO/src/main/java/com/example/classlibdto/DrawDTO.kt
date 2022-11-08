package com.example.classlibdto

import java.sql.Time

data class DrawDTO (
        val id:Int,
        val name:String,
        val image:String,
        val interestPoint: HashMap<Int, InterestPointDTO>,
        val creationDate: DateDTO,
        val lifeTime: Time,
        val authors:HashMap<Int, UserDTO>,
        val nbView:Int,
        val nbReport:Int
        )