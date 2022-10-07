package com.example.classlib

import java.sql.Time
import java.util.*
import kotlin.collections.HashMap

data class Draw(val id:Int,
                val name:String,
                val image:String,
                val interestPoint: HashMap<Int,InterestPoint>,
                val creationDate: Date,
                val lifeTime: Time,
                val authors:HashMap<Int,User>,
                val nbView:Int,
                val nbReport:Int)
