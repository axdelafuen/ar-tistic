package com.example.database

import com.example.classlib.Date

fun main(){
    val newUser = com.example.classlib.User(
        id = 1,
        name = "test",
        profilePicture = "test",
        email = "test@test.fr",
        password = "test",
        birthDate = Date(2003,3,13),
        nbReport = 0,
        subscribes = hashMapOf()
    )

    val newUser2 = com.example.classlib.User(
        id = 2,
        name = "User2",
        profilePicture = "test2",
        email = "test2@test2.fr",
        password = "psswd2",
        birthDate = Date(2003,3,13),
        nbReport = 0,
        subscribes = hashMapOf()
    )

    val datatest = DatabasePersistanceDAO()

    datatest.createUser(newUser)
    datatest.createUser(newUser2)

    datatest.userFollows(1,2)
    val testUser = datatest.getUserById(1)

    print("Name:\n")
    print(testUser!!.name)
    print("\nID:\n")
    print(testUser!!.id)
    print("\nAnnée naissance:\n")
    print(testUser!!.birthDate!!.year)
    print("\nPassword:\n")
    print(testUser!!.password)
    print("\nEmail:\n")
    print(testUser!!.email)
    print("\nProfilePicture:\n")
    print(testUser!!.profilePicture)
    print("\nNbReport:\n")
    print(testUser!!.nbReport)
    print("\nSubscribes:\n")
    testUser!!.subscribes.forEach {
        print("\n--------------\n")
        print("\nName:\n")
        print(it.value.name)
        print("\nID:\n")
        print(it.value.id)
        print("\nAnnée naissance:\n")
        print(it.value.birthDate.year)
        print("\nPassword:\n")
        print(it.value.password)
        print("\nEmail:\n")
        print(it.value.email)
        print("\nProfilePicture:\n")
        print(it.value.profilePicture)
        print("\nNbReport:\n")
        print(it.value.nbReport)
        print("\nSubscribes:\n")
        print(it.value.subscribes)
    }

    print("\n-------------------------------------------------\n")
    print("Update user 1: change email to toto@tutu.fr\n")
    datatest.updateUser(1, com.example.classlib.User(
        id = 1,
        name = "test",
        profilePicture = "test",
        email = "toto@tutu.fr",
        password = "test",
        birthDate = Date(2003,3,13),
        nbReport = 0,
        subscribes = hashMapOf()
    ))

    print(datatest.getUserById(1)!!.email)

    print("\n-------------------------------------------------\n")
    print("Test Delete User 1\n")
    datatest.deleteUser(1)

    print("\nTest find by psswd & login user 2:\n")
    print(datatest.findUserByLogPswd("User2","psswd2").name)
    print("\n")
    print(datatest.loadData().users)
}