package com.example.database

import com.example.classlib.Collection
import com.example.classlib.Date
import com.example.classlib.IPersistenceManager
import com.example.classlib.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class DatabasePersistanceDAO : IPersistenceManager{

    val url = "jdbc:mysql:"+System.getenv("DB_SERVER")
    val user = System.getenv("DB_USER")
    val password = System.getenv("DB_PASSWORD")
/*
    val url = "jdbc:mysql://localhost:3306/sqlsaetest"
    val user = "root"
    val password = "root1234"
*/
    fun loadData(): Collection {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var userHashmap:HashMap<Int, com.example.classlib.User> = hashMapOf()
        transaction {
            val allUsers = com.example.database.User.all().forEach{
                userHashmap.put(it.id.value, getUserById(it.id.value)!!)
            }
        }
        return Collection(userHashmap, hashMapOf(), hashMapOf())
    }

    override fun getUserById(idUser: Int): User? {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var count: Long = 0
        var userList = ArrayList<com.example.database.User>()
        var hmsub: HashMap<Int,com.example.classlib.User> = hashMapOf()
        transaction {
            val usr = com.example.database.User.findById(idUser)
            ActionDone.find{ (ActionsDone.vcreator eq true) and (ActionsDone.vidUser eq idUser)}.forEach{
                val reported = ActionDone.find { (ActionsDone.vidDraw eq it.iddraw) and (ActionsDone.vreport eq true) }.count()
                count += reported
            }
            if (usr != null) {
                userList.add(usr)
            }

            Relation.find{ (Relations.vfollow eq true) and (Relations.vidUser eq idUser)}.forEach {
                hmsub.put(it.idusercible.value,getUserByIdNoSubs(it.idusercible.value))
            }
        }

       return userDataToUserClass(userList[0],hmsub, count.toInt(),true)
    }

    override fun getuserByEmail(content: String): User? {
        TODO("Not yet implemented")
    }

    fun getUserByIdNoSubs(idUser: Int): com.example.classlib.User{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var count: Long = 0
        var userList = ArrayList<com.example.database.User>()
        transaction {
            val usr = com.example.database.User.findById(idUser)
            ActionDone.find{ (ActionsDone.vcreator eq true) and (ActionsDone.vidUser eq idUser)}.forEach{
                val reported = ActionDone.find { (ActionsDone.vidDraw eq it.iddraw) and (ActionsDone.vreport eq true) }.count()
                count += reported
            }
            if (usr != null) {
                userList.add(usr)
            }

        }

        return userDataToUserClass(userList[0], hashMapOf(), count.toInt(),false)
    }
    override fun createUser(usr: User) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            com.example.database.User.new {
                name = usr.name
                email = usr.email
                profilePicture = usr.profilePicture
                password = usr.password
                birthdate = LocalDate.of(usr.birthDate.year,usr.birthDate.month,usr.birthDate.day)
            }
        }
    }

    fun userFollows(idUser: Int, idUserCible:Int){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            Relation.new {
                iduser = EntityID(idUser,Users)
                idusercible = EntityID(idUserCible,Users)
                follow = true
            }
        }
    }

    override fun updateUser(id: Int, usr: User) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var userToUpdate = com.example.database.User.findById(id)
            userToUpdate!!.name = usr.name
            userToUpdate!!.email = usr.email
            userToUpdate!!.password = usr.password
            userToUpdate!!.profilePicture = usr.profilePicture
            userToUpdate!!.birthdate = LocalDate.of(usr.birthDate.year,usr.birthDate.month,usr.birthDate.day)
        }

    }

    override fun deleteUser(id: Int) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            val userToDelete = com.example.database.User.findById(id)
            Relations.deleteWhere { vidUser eq id }
            Relations.deleteWhere { vidUserCible eq id }
            Collaborateds.deleteWhere { vidUser eq id }
            Noteds.deleteWhere { vidUser eq id }
            Commenteds.deleteWhere { vidUser eq id }
            ActionsDone.deleteWhere { vidUser eq id }

            userToDelete!!.delete()
        }
    }

    override fun findUserByLogPswd(log: String, psswrd: String): User {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var userList = ArrayList<com.example.database.User>()
        transaction {
            val usr = com.example.database.User.find { (Users.vname eq log) and (Users.vpassword eq psswrd) }.forEach{
                userList.add(it)
            }
        }

        return getUserById(userList[0].id.value)!!
    }

    fun userDataToUserClass(u: com.example.database.User, hmsub: HashMap<Int,com.example.classlib.User> = hashMapOf(), nbR: Int, subBool: Boolean):com.example.classlib.User {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        if (subBool) {
            val returnedUser = com.example.classlib.User(
                id = u.id.value,
                name = u.name,
                email = u.email,
                profilePicture = u.profilePicture,
                password = u.password,
                birthDate = Date(u.birthdate.year, u.birthdate.monthValue, u.birthdate.dayOfMonth),
                nbReport = nbR,
                subscribes = hmsub
            )
          //  print(returnedUser.birthDate.year)
            return returnedUser
        }
        val returnedUser = com.example.classlib.User(
            id = u.id.value,
            name = u.name,
            email = u.email,
            profilePicture = u.profilePicture,
            password = u.password,
            birthDate = Date(u.birthdate.year, u.birthdate.monthValue, u.birthdate.dayOfMonth),
            nbReport = nbR,
            subscribes = hashMapOf()
        )
        return returnedUser
    }

}