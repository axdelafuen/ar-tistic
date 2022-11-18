package com.example.classlib

import User
import java.io.Serializable

class LocalPers(var usr:User): Serializable {
    init {
        this.usr=usr
    }
    fun saveData(){

    }
}