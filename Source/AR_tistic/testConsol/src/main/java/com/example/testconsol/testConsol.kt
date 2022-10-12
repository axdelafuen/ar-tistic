package com.example.testconsol
import com.example.classlib.*
import com.example.stub.*
import java.util.*

fun main(){
    var stub=Stub()
    for(usr in stub.loadData().users){
        println(usr.value.name)
    }
    println("----")
    for(pt in stub.loadData().interestPoints){
        println(pt.value.name)
    }
    println("----")
    for(usr in stub.loadData().draws){
        println(usr.value.name)
    }
    println("----")
    var ret = stub.loadUsersIndex(1,1)
    for(usrs in ret){
        println(usrs.value.name)
    }
}
