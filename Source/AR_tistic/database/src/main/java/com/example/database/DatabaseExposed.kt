package com.example.database

import com.example.stub.Stub
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction


fun main(){
    Database.connect(
        url= "jdbc:mysql://localhost:3306/sqlsaetest",
        user = "root",
        password = "root1234"
    )

    transaction {
        addLogger(StdOutSqlLogger)

        // DROP TABLES

        SchemaUtils.drop(Collaborated)
        SchemaUtils.drop(ActionDone)
        SchemaUtils.drop(Relation)
        SchemaUtils.drop(Commented)
        SchemaUtils.drop(Noted)
        SchemaUtils.drop(User)
        SchemaUtils.drop(Drawing)
        SchemaUtils.drop(InterestPoint)

        //  CREATE TABLES

        SchemaUtils.create(User)
        SchemaUtils.create(Drawing)
        SchemaUtils.create(Noted)
        SchemaUtils.create(InterestPoint)
        SchemaUtils.create(Commented)
        SchemaUtils.create(Relation)
        SchemaUtils.create(Collaborated)
        SchemaUtils.create(ActionDone)
    }

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
}

object User : Table(){
    val id = integer("id")
    val username =  varchar("username", 20)
    val profilePicture = varchar("profile_picture", 200)
    val DoB = date("date_of_Birth")
    val password = varchar("password", 50)
    val mail = varchar("email", 50)
    val nbReport = integer("number_of_Report")
    override val primaryKey = PrimaryKey(id)
}

object Drawing: Table(){
    val id = integer("id")
    val nbViews = integer("nb_views")
    val creationDate = date("date_creation")
    val image = varchar("image", 200)
    val lifetime = integer("lifetime")
    val nom = varchar("name", 50)
    val InterestPointCreation = varchar("id_interest_point", 20) references InterestPoint.id
    override val primaryKey = PrimaryKey(id)
}

object Relation: Table(){
    val idUser = integer("id_user") references User.id
    val idUserCible = integer("id_user_cible") references User.id
    val followCible = bool("follow_cible")
    override val primaryKey = PrimaryKey(idUser, idUserCible)
}

object InterestPoint: Table(){
    val id = varchar("id", 20)
    val latitude = float("latitude")
    val longitude = float("longitude")
    val desc = varchar("desc", 100)
    val name = varchar("name", 50)
    val image = varchar("image", 200)
    override val primaryKey = PrimaryKey(id)
}

object Commented: Table(){
    val idUser = integer("id_user") references User.id
    val drawing = integer("drawing") references Drawing.id
    val date = date("date")
    val message = varchar("message", 100)
    override val primaryKey = PrimaryKey(idUser, drawing)
}

object Noted: Table(){
    val idUser = integer("id_user") references User.id
    val drawing = integer("drawing") references Drawing.id
    val note = float("note")
    override val primaryKey = PrimaryKey(idUser, drawing)
}

object Collaborated: Table(){
    val idUser = integer("id_user") references User.id
    val drawing = integer("drawing") references Drawing.id
    val dateModif = date("date_modif")
    override val primaryKey = PrimaryKey(idUser, drawing)
}

object ActionDone: Table(){
    val idUser = integer("id_user") references User.id
    val drawing = integer("drawing") references Drawing.id
    val report = bool("report")
    val like = bool("like")
    val creator = bool("creator")
    override val primaryKey = PrimaryKey(idUser, drawing)
}