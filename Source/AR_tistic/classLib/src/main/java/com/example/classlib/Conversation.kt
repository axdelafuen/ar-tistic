package com.example.classlib

data class Conversation(val id:Int,
                        val participant:HashMap<Int,User>,
                        val messages:HashMap<Int,Message>

)