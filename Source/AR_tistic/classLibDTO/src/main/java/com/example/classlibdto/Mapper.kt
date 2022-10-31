package com.example.classlibdto

import com.example.classlib.*

class Mapper {
    fun mapUser(userDTO: UserDTO):User{
        return User(
            id = userDTO.id,
            name = userDTO.name,
            profilePicture = userDTO.profilePicture,
            email = userDTO.email,
            password = userDTO.password,
            birthDate = userDTO.birthDate,
            subscribes = userDTO.subscribes,
            nbReport = userDTO.nbReport
        )
    }

    fun mapInterestPoint(interestPointDTO: InterestPointDTO):InterestPoint{
        return InterestPoint(
            id = interestPointDTO.id,
            name = interestPointDTO.name,
            desc = interestPointDTO.desc,
            latitude = interestPointDTO.latitude,
            longitude = interestPointDTO.longitude,
            picture = interestPointDTO.picture
        )
    }

    fun mapDraw(drawDTO:DrawDTO):Draw{
        return Draw(
            id = drawDTO.id,
            name = drawDTO.name,
            image = drawDTO.image,
            interestPoint = drawDTO.interestPoint,
            creationDate = drawDTO.creationDate,
            lifeTime = drawDTO.lifeTime,
            authors = drawDTO.authors,
            nbView = drawDTO.nbView,
            nbReport = drawDTO.nbReport
        )
    }
}