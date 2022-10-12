package com.example.testconsol
import com.example.classlib.*
import com.example.stub.*
import java.util.*

fun main(){
    var UserHashMap=hashMapOf(
        0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate= Date(1999,2,2) , subscribes= hashMapOf(), nbReport = 0 ),
        1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        2 to User(id= 2, name = "Patrick", profilePicture = "./img/pp/Fredo.jpg", email = "patrick@gmail.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        3 to User(id= 3, name = "Nina", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        4 to User(id= 4, name = "Louis", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
        5 to User(id= 5, name = "1", profilePicture = "./img/pp/Fredo.jpg", email = "2", password="1", birthDate= Date(2003,1,1), subscribes = hashMapOf(), nbReport = 0 ),
    )
    var stub=Stub()
    //Test User
    //test get
    for (usr in stub.loadUsers().values){
        println(usr.name)
    }
}
