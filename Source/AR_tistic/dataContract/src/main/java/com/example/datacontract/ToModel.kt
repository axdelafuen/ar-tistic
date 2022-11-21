package com.example.datacontract

import com.example.classlib.*
import com.example.classlibdto.*

fun toModel(user:UserDTO):User{
   return User(user.id,user.email,user.profilePicture, user.name,user.password,user.birthDate,user.subscribes,user.nbReport);
}