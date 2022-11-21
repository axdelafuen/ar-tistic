package com.example.classlib

import java.io.Serializable

class LocalPers(var usr:User): Serializable {
    init {
        this.usr=usr
    }
    fun saveData(){

    }
}