package com.example.classlibdto

import java.sql.Time
import com.example.classlib.*

data class DrawDTO (
        val id:Int,
        val name:String,
        val image:String,
        val interestPoint: HashMap<Int, InterestPoint>,
        val creationDate: Date,
        val lifeTime: Time,
        val authors:HashMap<Int, User>,
        val nbView:Int,
        val nbReport:Int
        )