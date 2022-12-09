package com.example.database

import com.example.classlib.*
import com.example.classlib.Collection
import com.example.classlib.Date
import com.example.classlib.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DatabasePersistanceDAO : IPersistenceManager{
    val url = "jdbc:mysql://"+System.getenv("DB_SERVER")+"/"+System.getenv("DB_DATABASE")
    val user = "root"
    val password = System.getenv("DB_ROOT_PASSWORD")

    /*
        val url = "jdbc:mysql:"+System.getenv("DB_SERVER")
        val user = System.getenv("DB_USER")
        val password = System.getenv("DB_PASSWORD")
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
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var userList = ArrayList<com.example.database.User>()
        transaction {
            val usr = com.example.database.User.find { (Users.vemail eq content) }.forEach{
                userList.add(it)
            }
        }

        return getUserById(userList[0].id.value)!!
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
            val usr = com.example.database.User.find { (Users.vemail eq log) or (Users.vname eq log) and (Users.vpassword eq psswrd) }.forEach{
                userList.add(it)
            }
        }

        return getUserById(userList[0].id.value)!!
    }

    override fun getLikes(id: Int): Int {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var count: Int = 0

        transaction {
            ActionDone.find{ (ActionsDone.vcreator eq true) and (ActionsDone.vidUser eq id)}.forEach{
                var liked = ActionDone.find { (ActionsDone.vidDraw eq it.iddraw) and (ActionsDone.vlike eq true) }.count().toInt()
                count += liked
            }
        }
        return count
    }

    override fun getFollowers(id: Int): Int {
        return 1//TODO
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

    override fun createDraw(draw: com.example.classlib.Draw) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            com.example.database.Draw.new {
                name = draw.name
                image = draw.image
                lifetime = LocalTime.of(draw.lifeTime.hours,draw.lifeTime.minutes,draw.lifeTime.seconds)
                creationDate = LocalDate.of(draw.creationDate.year,draw.creationDate.month,draw.creationDate.day)
                nbViews = 1
            }

            com.example.database.ActionDone.new{
                iduser = EntityID(draw.authors.getValue(0).id,Users)
                iddraw = EntityID(draw.id,Draws)
                creator = true
                report = false
                like = false
            }

            Collaborated.new {
                iduser = EntityID(draw.authors.getValue(0).id,Users)
                iddraw = EntityID(draw.id,Draws)
            }
            CreatedOn.new {
                idinterestpoint = EntityID(draw.interestPoint.getValue(0).id,InterestPoints)
                iddraw = EntityID(draw.id,Draws)
             }
        }
    }

    override fun getCollaborated(idDraw: Int): HashMap<Int,com.example.classlib.User>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var authorsHash: HashMap<Int,com.example.classlib.User> = hashMapOf()
        transaction {
            Collaborated.find { Collaborateds.vidDraw eq idDraw }.forEach {
                authorsHash.put(it.iduser.value, getUserById(it.iduser.value)!!)
            }
        }
        return authorsHash
    }

    override fun getDrawById(idDraw: Int): com.example.classlib.Draw? {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var nbR: Int = 0
        var drawsList = ArrayList<com.example.database.Draw>()
        var authorsHash: HashMap<Int,com.example.classlib.User> = hashMapOf()
        transaction {
            val draw = com.example.database.Draw.findById(idDraw)
            if (draw != null) {
                drawsList.add(draw)
            }
            ActionDone.find { (ActionsDone.vidDraw eq idDraw) and (ActionsDone.vreport eq true) }.forEach {
                nbR+=1
            }
        }

        return drawDataToDrawClass(drawsList[0], nbR)

    }

    private fun getDrawInterestPoints (idDraw: Int): HashMap<Int,com.example.classlib.InterestPoint>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var hashIP: HashMap<Int,com.example.classlib.InterestPoint> = hashMapOf()
        transaction {
            CreatedOn.find { CreatedsOn.vidDraw eq idDraw }.forEach {
                hashIP.put(it.idinterestpoint.value, getInterestPointById(it.idinterestpoint.value))
            }
        }
        
        return hashIP
    }

    override fun deleteDraw(idDraw: Int){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var drawToDelete = Draw.findById(idDraw)
            com.example.database.ActionsDone.deleteWhere{ vidDraw eq idDraw}
            com.example.database.Collaborateds.deleteWhere{ vidDraw eq idDraw}
            drawToDelete!!.delete()
        }
    }

/*    override fun updateDraw(d: com.example.classlib.Draw) {
        TODO("Not yet implemented")
    }*/


    override fun updateDraw(d: com.example.classlib.Draw){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var drawToUpdate = Draw.findById(d.id)
            drawToUpdate!!.name = d.name
            drawToUpdate!!.image = d.image
            drawToUpdate!!.creationDate = LocalDate.of(d.creationDate.year,d.creationDate.month,d.creationDate.day)
            drawToUpdate!!.lifetime = LocalTime.of(d.lifeTime.hours,d.lifeTime.minutes,d.lifeTime.seconds)
            drawToUpdate!!.nbViews = d.nbView
        }

        transaction {
            d.interestPoint.forEach {
                var createdOnToUpdate = CreatedOn.find { (CreatedsOn.vidDraw eq d.id) and (CreatedsOn.vInterestPoint eq it.key) }
                if(createdOnToUpdate.empty()){
                    CreatedOn.new {
                        iddraw = EntityID(d.id,com.example.database.Draws)
                        idinterestpoint = EntityID(it.key,InterestPoints)
                    }
                }
                CreatedOn.find { (CreatedsOn.vidDraw neq d.id) and (CreatedsOn.vInterestPoint neq it.key)  }.forEach {
                    it.delete()
                }
            }
        }
    }

    private fun drawDataToDrawClass(d: com.example.database.Draw, nbReport: Int):com.example.classlib.Draw?{
        if(d == null){return null}
        return com.example.classlib.Draw(
            name = d.name,
            id = d.id.value,
            lifeTime = Time(d.lifetime.hour,d.lifetime.minute,d.lifetime.second),
            creationDate = Date(d.creationDate.year,d.creationDate.monthValue,d.creationDate.dayOfMonth),
            image = d.image,
            authors = getCollaborated(d.id.value),
            nbReport = nbReport,
            nbView = d.nbViews,
            interestPoint = getDrawInterestPoints(d.id.value)
        )
    }

    private fun getInterestPointById(idIP: Int): com.example.classlib.InterestPoint{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var ipList :ArrayList<com.example.classlib.InterestPoint> = arrayListOf()
        transaction {
            ipList.add(interestPointDataToInterestPointClass(InterestPoint.findById(idIP)!!))
        }
        return ipList[0]
    }

    private fun interestPointDataToInterestPointClass(ip: com.example.database.InterestPoint): com.example.classlib.InterestPoint{
        return com.example.classlib.InterestPoint(
            id = ip.id.value,
            name = ip.name,
            desc = ip.description,
            latitude = ip.latitude,
            longitude = ip.longitude,
            picture = ip.image
        )
    }

    override fun patternRecognitionUsers(pattern: String): HashMap<Int,com.example.classlib.User>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var hashUsers: HashMap<Int,com.example.classlib.User> = hashMapOf()
        transaction {
            com.example.database.User.find { Users.vname like ".*${pattern}.*" }.forEach {
                hashUsers.put(it.id.value, getUserById(it.id.value)!!)
            }
        }
        return hashUsers
    }


}