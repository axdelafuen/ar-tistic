package com.example.datacontract

import com.example.classlib.*
import com.example.classlibdto.*

fun toDTO(user:User):UserDTO{
    return UserDTO(user.id,user.name,user.profilePicture,user.email,user.password,user.birthDate,user.subscribes,user.nbReport);
}

fun toDTO(users:HashMap<Int,User>):HashMap<Int,UserDTO>{
    var res = HashMap<Int,UserDTO>();
    for(user in users){
        res.put(user.key,toDTO(user.value));
    }
    return res;
}
