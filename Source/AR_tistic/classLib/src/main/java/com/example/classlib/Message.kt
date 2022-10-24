package com.example.classlib

data class Message(val id:Int,
                   val content:String,
                   val date: Date,
                   val sender:User
)