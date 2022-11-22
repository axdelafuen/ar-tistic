package com.example.classlib

import java.io.Serializable

class Evaluation(
    override val id: Int,
    override val content: String,
    override val author: User,
    override val draw: Draw,
    val grade:Int
): Comment(id,content,author, draw), Serializable