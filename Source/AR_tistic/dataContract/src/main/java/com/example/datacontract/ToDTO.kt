package com.example.datacontract

import com.example.classlib.*
import com.example.classlibdto.*

fun toDTO(user:User): UserDTO {
    return UserDTO(user.id,user.email,user.profilePicture,user.name,user.password,user.birthDate,user.subscribes,user.nbReport);
}

@JvmName("toDTO1")
fun toDTO(users:HashMap<Int,User>):HashMap<Int,UserDTO>{
    val res = HashMap<Int,UserDTO>();
    for(user in users) {
        res.put(user.key, toDTO(user.value));
    }
    return res;
}

fun toDTO(users:Array<User>?):Array<UserDTO>{
    lateinit var res:Array<UserDTO>
    for(user in users!!) {
        res.set(user.id, toDTO(user))
    }
    return res;
}
fun toDTO(draw:Draw): DrawDTO {
    return DrawDTO(draw.id,draw.name, draw.image,draw.interestPoint,draw.creationDate, draw.lifeTime, draw.authors,draw.nbView,draw.nbReport);
}

fun toDTO(draws:HashMap<Int,Draw>):HashMap<Int,DrawDTO>{
    val res = HashMap<Int,DrawDTO>();
    for(user in draws) {
        res.put(user.key, toDTO(user.value));
    }
    return res;
}
