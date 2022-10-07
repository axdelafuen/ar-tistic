package com.example.stub

import com.example.classlib.*

interface IPersistancemanager {
    public fun saveData(users:List<User>);
    public fun loadData():(List<User>);
}