package com.example.testconsol
import com.example.classlib.*
import com.example.stub.*
import java.util.*

fun main(){
    var stub=Stub()
    println("----Load of all users")
    for(usr in stub.loadData().users){
        println(usr.value.name)
    }
    println("----Load of all interest point")
    for(pt in stub.loadData().interestPoints){
        println(pt.value.name)
    }
    println("----Load of all draws")
    for(usr in stub.loadData().draws){
        println(usr.value.name)
    }
    println("----Load of users by idx")
    var idxUs = stub.loadUsersIndex(0,1)
    for(usrs in idxUs){
        println(usrs.value.name)
    }
    println("----Load of interest point by idx")
    var idxPt = stub.loadInterestPointsIndex(1,2)
    for(pt in idxPt){
        println(pt.value.name)
    }
    println("----Load of draws by idx")
    var idxDraw = stub.loadDrawsIndex(1,1)
    for(dra in idxDraw){
        println(dra.value.name)
    }
    println("----get user by id")
    println(stub.getUserById(2))
    println("----post user")
    stub.createUser(User(id= 12, name = "Jaqui", profilePicture = "./img/pp/Fredo.jpg", email = "patrick@gmail.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ))
    println(stub.getUserById(6))
    println("----put user")
    stub.updateUser(2,User(id= 1, name = "PatPat", profilePicture = "./img/pp/Fredo.jpg", email = "patrick@gmail.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ))
    println(stub.getUserById(1))
    println(stub.getUserById(2))
    println("----delete user")
    println(stub.getUserById(2))
    println(stub.userHashMap.size)
    stub.deleteUser(2)
    println(stub.userHashMap.size)
    println(stub.getUserById(2))

}
