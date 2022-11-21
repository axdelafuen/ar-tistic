package com.example.classlib

import java.io.Serializable

class Manager(var persistence:IPersistenceManager, var usr:User): Serializable {
    init {
        this.persistence=persistence;
        this.usr=usr;
    }
}