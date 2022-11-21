package com.example.classlib

import User

abstract class Comment(
     open val id: Int,
     open val content: String,
     open val author: User,
     open val draw: Draw
)