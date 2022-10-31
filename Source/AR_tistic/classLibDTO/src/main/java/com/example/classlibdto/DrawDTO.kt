package com.example.classlibdto

import com.example.classlib.Date
import com.example.classlib.InterestPoint
import com.example.classlib.User
import java.sql.Time

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