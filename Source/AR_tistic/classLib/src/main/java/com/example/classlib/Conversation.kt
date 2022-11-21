package com.example.classlib

class Conversation(val id:Int,
                        val participant:HashMap<Int,User>,
                        val messages:HashMap<Int,Message>

)