package com.example.stub

import com.example.classlib.*

interface IPersistancemanager {
    fun loadData():(HashMap<Int,User>);
}