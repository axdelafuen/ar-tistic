package com.example.classlib

import User

class Manager(var persistence:IPersistenceManager, var usr:User) {
    init {
        this.persistence=persistence;
        this.usr=usr;
    }
}