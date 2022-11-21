package com.example.classlib

import java.io.Serializable

class Manager(var persistence:IPersistenceManager): Serializable {
    var usr:User = User(0, "0", "@drawable/pp_edit","0","0", Date(0,0,0), hashMapOf(),0 )//currrent User, transfert to other views no need to reload
    init {
        this.persistence=persistence;
    }
}