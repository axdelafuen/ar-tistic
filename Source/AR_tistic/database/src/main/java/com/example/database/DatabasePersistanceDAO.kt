package com.example.database

import com.example.classlib.*
import com.example.classlib.Collection
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalTime

class DatabasePersistanceDAO : IPersistenceManager{
    val url = "jdbc:mysql://"+System.getenv("DB_SERVER")+"/"+System.getenv("DB_DATABASE")
    val user = "root"
    val password = System.getenv("DB_ROOT_PASSWORD")

/*    val url = "jdbc:mysql://localhost:3306/sqlsaetest"
    val user = "root"
    val password = "root1234"*/
    fun loadData(): Collection {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var userHashmap:HashMap<Int, User> = hashMapOf()
        transaction {
            t_User.all().forEach{
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
        var userList = ArrayList<t_User>()
        var hmsub: HashMap<Int, User> = hashMapOf()
        transaction {
            val usr = t_User.findById(idUser)
            t_ActionDone.find{ (t_ActionsDone.vcreator eq true) and (t_ActionsDone.vidUser eq idUser)}.forEach{
                val reported = t_ActionDone.find { (t_ActionsDone.vidDraw eq it.iddraw) and (t_ActionsDone.vreport eq true) }.count()
                count += reported
            }
            if (usr != null) {
                userList.add(usr)
            }

            t_Relation.find{ (t_Relations.vfollow eq true) and (t_Relations.vidUser eq idUser)}.forEach {
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

        var userList = ArrayList<t_User>()
        transaction {
            t_User.find { (t_Users.vemail eq content) }.forEach{
                userList.add(it)
            }
        }

        return getUserById(userList[0].id.value)!!
    }

    fun getUserByIdNoSubs(idUser: Int): User{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var count: Long = 0
        var userList = ArrayList<t_User>()
        transaction {
            val usr = t_User.findById(idUser)
            t_ActionDone.find{ (t_ActionsDone.vcreator eq true) and (t_ActionsDone.vidUser eq idUser)}.forEach{
                val reported = t_ActionDone.find { (t_ActionsDone.vidDraw eq it.iddraw) and (t_ActionsDone.vreport eq true) }.count()
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
            t_User.new {
                name = usr.name
                email = usr.email
                profilePicture = usr.profilePicture
                password = usr.password
                birthdate = LocalDate.of(usr.birthDate.year,usr.birthDate.month,usr.birthDate.day)
            }
        }
    }

    override fun userFollows(idUser: Int, idUserCible:Int){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            t_Relation.new {
                iduser = EntityID(idUser, t_Users)
                idusercible = EntityID(idUserCible, t_Users)
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
            var userToUpdate = t_User.findById(id)
            userToUpdate!!.name = usr.name
            userToUpdate.email = usr.email
            userToUpdate.password = usr.password
            userToUpdate.profilePicture = usr.profilePicture
            userToUpdate.birthdate = LocalDate.of(usr.birthDate.year,usr.birthDate.month,usr.birthDate.day)
        }

    }

    override fun deleteUser(id: Int) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            val userToDelete = t_User.findById(id)
            t_Relations.deleteWhere { vidUser eq id }
            t_Relations.deleteWhere { vidUserCible eq id }
            t_Collaborateds.deleteWhere { vidUser eq id }
            t_Noteds.deleteWhere { vidUser eq id }
            t_Commenteds.deleteWhere { vidUser eq id }
            t_ActionsDone.deleteWhere { vidUser eq id }

            userToDelete!!.delete()
        }
    }

    override fun findUserByLogPswd(log: String, psswrd: String): User {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var userList = ArrayList<t_User>()
        transaction {
            t_User.find { (t_Users.vemail eq log) or (t_Users.vname eq log) and (t_Users.vpassword eq psswrd) }.forEach{
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

        var count = 0

        transaction {
            t_ActionDone.find{ (t_ActionsDone.vcreator eq true) and (t_ActionsDone.vidUser eq id)}.forEach{
                var liked = t_ActionDone.find { (t_ActionsDone.vidDraw eq it.iddraw) and (t_ActionsDone.vlike eq true) }.count().toInt()
                count += liked
            }
        }
        return count
    }

    override fun userLikesDrawing(idUser: Int, idDraw: Int){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var result = t_ActionDone.find { (t_ActionsDone.vidUser eq idUser) and (t_ActionsDone.vidDraw eq idDraw)}

            if( result.count().toInt() == 0){
                t_ActionDone.new {
                    iduser = EntityID(idUser,t_Users)
                    iddraw = EntityID(idDraw,t_Draws)
                    report = false
                    like = true
                    creator = false
                }
            }
            else{
                result.forEach {
                    it.like = true
                }
            }
        }
    }

    override fun getFollowers(id: Int): HashMap<Int, User> {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var returnedHashmap:HashMap<Int,User> = hashMapOf()
        transaction{
            t_Relation.find{ (t_Relations.vfollow eq true) and (t_Relations.vidUserCible eq id)}.forEach {
                returnedHashmap.put(it.iduser.value, getUserById(it.iduser.value)!!)
            }
        }
        return returnedHashmap
    }

    override fun getNbFollowers(id:Int) : Int{
        return getFollowers(id).count()
    }

    fun userDataToUserClass(u: t_User, hmsub: HashMap<Int, User> = hashMapOf(), nbR: Int, subBool: Boolean): User {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        if (subBool) {
            val returnedUser = User(
                id = u.id.value,
                name = u.name,
                email = u.email,
                profilePicture = u.profilePicture,
                password = u.password,
                birthDate = Date(u.birthdate.year, u.birthdate.monthValue, u.birthdate.dayOfMonth),
                nbReport = nbR,
                subscribes = hmsub
            )
            return returnedUser
        }
        val returnedUser = User(
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

    override fun createDraw(draw: Draw) {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var newdraw =  t_Draw.new {
                name = draw.name
                image = draw.image
                lifetime = LocalTime.of(draw.lifeTime.hours,draw.lifeTime.minutes,draw.lifeTime.seconds)
                creationDate = LocalDate.of(draw.creationDate.year,draw.creationDate.month,draw.creationDate.day)
                nbViews = 1
            }

            t_ActionDone.new{
                iduser = EntityID(draw.authors.keys.first(),t_Users)
                iddraw = EntityID(newdraw.id.value,t_Draws)
                creator = true
                report = false
                like = false
            }

            draw.authors.forEach {
                t_Collaborated.new {
                    iduser = EntityID(it.key,t_Users)
                    iddraw = EntityID(newdraw.id.value,t_Draws)
                }
            }

            t_CreatedOn.new {
                idinterestpoint = EntityID(draw.interestPoint.keys.first(),t_InterestPoints)
                iddraw = EntityID(newdraw.id.value,t_Draws)
             }
        }
    }

    override fun getDrawFromUser(userId: Int): HashMap<Int, Draw>? {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var userDraws:HashMap<Int, Draw>? = hashMapOf()
        transaction {
            t_ActionDone.find{ (t_ActionsDone.vidUser eq userId)}.forEach {
                t_Draw.find { (t_Draws.id eq it.id) }.forEach {
                    userDraws!![it.id.value] = getDrawById(it.id.value)!!
                }
            }
        }
        return userDraws
    }

    override fun getCollaborated(idDraw: Int): HashMap<Int, User>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var authorsHash: HashMap<Int, User> = hashMapOf()
        transaction {
            t_Collaborated.find { t_Collaborateds.vidDraw eq idDraw }.forEach {
                authorsHash[it.iduser.value] = getUserById(it.iduser.value)!!
            }
        }
        return authorsHash
    }

    override fun getDrawsInInterestPoint(idInterestPoint: Int): ArrayList<Draw> {
        TODO("Not yet implemented")
    }

    override fun getDrawById(idDraw: Int): Draw? {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var nbR = 0
        var drawsList = ArrayList<t_Draw>()
        transaction {
            val draw = t_Draw.findById(idDraw)
            if (draw != null) {
                drawsList.add(draw)
            }
            t_ActionDone.find { (t_ActionsDone.vidDraw eq idDraw) and (t_ActionsDone.vreport eq true) }.forEach {
                nbR+=1
            }
        }

        return drawDataToDrawClass(drawsList[0], nbR)

    }

    private fun getDrawInterestPoints (idDraw: Int): HashMap<Int, InterestPoint>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var hashIP: HashMap<Int, InterestPoint> = hashMapOf()
        transaction {
            t_CreatedOn.find { t_CreatedsOn.vidDraw eq idDraw }.forEach {
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
            var drawToDelete = t_Draw.findById(idDraw)
            t_ActionsDone.deleteWhere{ vidDraw eq idDraw}
            t_Collaborateds.deleteWhere{ vidDraw eq idDraw}
            drawToDelete!!.delete()
        }
    }



    override fun updateDraw(d: Draw){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            var drawToUpdate = t_Draw.findById(d.id)
            drawToUpdate!!.name = d.name
            drawToUpdate!!.image = d.image
            drawToUpdate!!.creationDate = LocalDate.of(d.creationDate.year,d.creationDate.month,d.creationDate.day)
            drawToUpdate!!.lifetime = LocalTime.of(d.lifeTime.hours,d.lifeTime.minutes,d.lifeTime.seconds)
            drawToUpdate!!.nbViews = d.nbView
        }

        transaction {
            d.interestPoint.forEach {
                var createdOnToUpdate = t_CreatedOn.find { (t_CreatedsOn.vidDraw eq d.id) and (t_CreatedsOn.vInterestPoint eq it.key) }
                if(createdOnToUpdate.empty()){
                    t_CreatedOn.new {
                        iddraw = EntityID(d.id,t_Draws)
                        idinterestpoint = EntityID(it.key,t_InterestPoints)
                    }
                }
                t_CreatedOn.find { (t_CreatedsOn.vidDraw neq d.id) and (t_CreatedsOn.vInterestPoint neq it.key)  }.forEach {
                    it.delete()
                }
            }

                var authorToUpdate = t_ActionDone.find { (t_ActionsDone.vidDraw eq d.id) and (t_ActionsDone.vidUser eq d.authors.keys.first()) and (t_ActionsDone.vcreator eq true) }
                if(authorToUpdate.empty()){
                    t_ActionDone.new {
                        iddraw = EntityID(d.id,t_Draws)
                        iduser = EntityID(d.authors.keys.first(),t_Users)
                        creator = true
                    }
                }

            val collaboratorsAlreadyDone:ArrayList<Int> = arrayListOf()
            d.authors.forEach {
                val collaboratorsTpUpdate = t_Collaborated.find { (t_Collaborateds.vidDraw eq d.id) and (t_Collaborateds.vidUser eq it.key)}
                if(collaboratorsTpUpdate.empty()){
                    t_Collaborated.new {
                        iduser = EntityID(it.key, t_Users)
                        iddraw = EntityID(d.id, t_Draws)
                    }
                }
                collaboratorsAlreadyDone.add(it.key)
            }

            t_Collaborated.find { (t_Collaborateds.vidDraw eq d.id) }.forEach {
                if(it.iduser.value !in collaboratorsAlreadyDone){
                    it.delete()
                }
            }


        }
    }

    private fun drawDataToDrawClass(d: t_Draw, nbReport: Int): Draw?{
        if(d == null){return null}
        return Draw(
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

    override fun createInterestPoint(ip: InterestPoint){
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        transaction {
            t_InterestPoint.new {
                name = ip.name
                description = ip.desc
                image = ip.picture
                latitude = ip.latitude
                longitude = ip.longitude
            }
        }
    }
    override fun getInterestPointById(idIP: Int): InterestPoint{
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        val ipList :ArrayList<InterestPoint> = arrayListOf()
        transaction {
            ipList.add(interestPointDataToInterestPointClass(t_InterestPoint.findById(idIP)!!))
        }
        return ipList[0]
    }

    override fun getInterestPointsByRange(rayon: Double, latitude: Double, longitude: Double): HashMap<Int, InterestPoint> {
        Database.connect(
            url = url,
            user = user,
            password = password
        )
        var returnedHashmap:HashMap<Int, InterestPoint> = hashMapOf()
        transaction {
            t_InterestPoint.find { (t_InterestPoints.vlatitude lessEq (latitude+rayon)) and (t_InterestPoints.vlongitude lessEq (longitude+rayon)) and (t_InterestPoints.vlongitude greaterEq longitude) and (t_InterestPoints.vlatitude greaterEq latitude) }.forEach {
                returnedHashmap.put(it.id.value, getInterestPointById(it.id.value))
            }
        }
        return returnedHashmap
    }

    override fun getDrawsByInterestPoint(idIP: Int) : HashMap<Int, Draw>{
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        var hmDraws:HashMap<Int, Draw> = hashMapOf()
        transaction {
            t_CreatedOn.find { t_CreatedsOn.vInterestPoint eq idIP }.forEach {
                hmDraws.put(it.iddraw.value, getDrawById(it.iddraw.value)!!)
            }
        }

        return hmDraws
    }

    override fun getAllUsers(): ArrayList<User> {
        TODO("Not yet implemented")
    }

    private fun interestPointDataToInterestPointClass(ip: t_InterestPoint): InterestPoint{
        return InterestPoint(
            id = ip.id.value,
            name = ip.name,
            desc = ip.description,
            latitude = ip.latitude,
            longitude = ip.longitude,
            picture = ip.image
        )
    }

    override fun patternRecognitionUsers(pattern: String): ArrayList<User> {
        Database.connect(
            url = url,
            user = user,
            password = password
        )

        val hashUsers: ArrayList<User> = ArrayList()
        transaction {
            //t_User.find { t_Users.vname like "%${pattern}%" }.forEach {
            t_User.find { t_Users.vemail like "%${pattern}%" }.forEach {
                   //hashUsers.put(it.id.value, getUserById(it.id.value)!!)
                    hashUsers.add(getUserById(it.id.value)!!)
                }
            }
        return hashUsers
    }

}