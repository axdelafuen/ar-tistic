package com.example.classlib

import User

data class Message(val id:Int,
                   val content:String,
                   val date: Date,
                   val sender:User
)