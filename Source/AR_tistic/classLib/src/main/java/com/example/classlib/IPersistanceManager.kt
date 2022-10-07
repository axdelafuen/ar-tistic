package com.example.classlib

interface IPersistancemanager {

    fun loadUsers():(HashMap<Int,User>)
}