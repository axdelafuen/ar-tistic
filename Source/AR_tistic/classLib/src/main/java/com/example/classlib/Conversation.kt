package com.example.classlib

import User

data class Conversation(val id:Int,
                        val participant:HashMap<Int,User>,
                        val messages:HashMap<Int,Message>

)