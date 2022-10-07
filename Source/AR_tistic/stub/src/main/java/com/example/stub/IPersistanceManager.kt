package com.example.stub

import com.example.classlib.*

interface IPersistancemanager {
    public fun loadData():(HashMap<Int,User>);
}