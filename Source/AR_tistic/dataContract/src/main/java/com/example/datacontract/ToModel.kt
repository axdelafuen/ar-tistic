package com.example.datacontract

import com.example.classlib.*
import com.example.classlibdto.*

fun toModel(user:UserDTO):User{
   return User(user.id,user.name,user.profilePicture, user.email,user.password,user.birthDate,user.subscribes,user.nbReport);
}