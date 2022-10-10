package com.example.classlib

import java.sql.Date

data class Message(val id:Int,
                   val content:String,
                   val date: Date,
                   val sender:User
)