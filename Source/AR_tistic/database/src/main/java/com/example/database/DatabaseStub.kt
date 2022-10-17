package com.example.database
import com.example.classlib.*
import com.example.stub.*
import com.example.classlib.Collection
import com.example.classlib.User
import org.ktorm.database.Database
import java.util.*
import kotlin.collections.HashMap


class DatabaseStub {

}

fun main(){

    var users = Stub().loadUsers()

    for((key,value) in users) {
        val id = value.id
        val name = value.name
        val profilePicture = value.profilePicture
        val email = value.email
        val password = value.password
        val birthDate = value.birthDate
        val subscribes = value.subscribes
        val nbReport = value.nbReport
    }
/*        Database.insert(com.example.database.User) {
            set(it.name, "jerry")
            set(it.job, "trainee")
            set(it.managerId, 1)
            set(it.hireDate, LocalDate.now())
            set(it.salary, 50)
            set(it.departmentId, 1)
        }*/
    docker container run -it --rm zenika/kotlin
}