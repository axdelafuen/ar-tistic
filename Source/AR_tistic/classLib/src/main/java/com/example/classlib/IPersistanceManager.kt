package com.example.classlib

interface IPersistancemanager {
    fun loadData():(HashMap<Int,User>);
}