package com.example.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalTime

fun main() {
    Database.connect(
        url = "jdbc:mysql://localhost:3306/sqlsaetest",
        user = "root",
        password = "root1234"
    )

    transaction {
        addLogger(StdOutSqlLogger)

        // DROP TABLES

        SchemaUtils.drop(Collaborateds)
        SchemaUtils.drop(ActionsDone)
        SchemaUtils.drop(Relations)
        SchemaUtils.drop(Commenteds)
        SchemaUtils.drop(Noteds)
        SchemaUtils.drop(Users)
        SchemaUtils.drop(Draws)
        SchemaUtils.drop(InterestPoints)

        //  CREATE TABLES

        SchemaUtils.create(Users)
        SchemaUtils.create(Draws)
        SchemaUtils.create(Noteds)
        SchemaUtils.create(InterestPoints)
        SchemaUtils.create(Commenteds)
        SchemaUtils.create(Relations)
        SchemaUtils.create(Collaborateds)
        SchemaUtils.create(ActionsDone)


    }


}

object Users: IntIdTable(){
    val vname: Column<String> = varchar("username",50)
    val vemail: Column<String> = varchar("email",100)
    val vprofilePicture: Column<String> = varchar("profilePicture",100)
    val vpassword: Column<String> = varchar("password", 50)
    val vbirthdate: Column<java.time.LocalDate> = date("date")
}

object Draws: IntIdTable(){
    val vname: Column<String> = varchar("imageName",50)
    val vimage: Column<String> = varchar("image",100)
    val vlifetime: Column<LocalTime> = time("password")
    val vcreationdate: Column<java.time.LocalDate> = date("date")
    val vinterestpoint: Column<Int> = integer("interestPointCreation").references(InterestPoints.id)
}

object InterestPoints: IntIdTable(){
    val vname: Column<String> = varchar("interestPointName",50)
    val vdescription: Column<String> = varchar("description",100)
    val vlatitude: Column<Double> = double("latitude")
    val vlongitude: Column<Double> = double("longitude")
    val vimage: Column<String> = varchar("interestPointImage",100)
}

object Relations: IntIdTable(){
    val vidUser: Column<Int> = integer("idUser").references(Users.id)
    val vidUserCible: Column<Int> = integer("idUserCible").references(Users.id)
    val vfollow: Column<Boolean> = bool("follow")
}

object ActionsDone: IntIdTable(){
    val vidUser: Column<Int> = integer("idUser").references(Users.id)
    val vidDraw: Column<Int> = integer("idDraw").references(Draws.id)
    val vreport: Column<Boolean> = bool("report")
    val vlike: Column<Boolean> = bool("like")
    val vcreator: Column<Boolean> = bool("creator")
}

object Noteds: IntIdTable() {
    val vidUser: Column<Int> = integer("idUser").references(Users.id)
    val vidDraw: Column<Int> = integer("idDraw").references(Draws.id)
    val vnote: Column<Float> = float("note")
}

object Collaborateds: IntIdTable() {
    val vidUser: Column<Int> = integer("idUser").references(Users.id)
    val vidDraw: Column<Int> = integer("idDraw").references(Draws.id)
}

object Commenteds: IntIdTable(){
    val vidUser: Column<Int> = integer("idUser").references(Users.id)
    val vidDraw: Column<Int> = integer("idDraw").references(Draws.id)
    val vdate: Column<java.time.LocalDate> = date("dateCommentaire")
    val vmsg: Column<String> = varchar("commentaire", 250)
}

class User(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<User>(Users)
    var name by Users.vname
    var email by Users.vemail
    var profilePicture by Users.vprofilePicture
    var password by Users.vpassword
    var birthdate by Users.vbirthdate
}

class InterestPoint(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<com.example.database.InterestPoint>(InterestPoints)
    var name by InterestPoints.vname
    var latitude by InterestPoints.vlatitude
    var longitude by InterestPoints.vlongitude
    var description by InterestPoints.vdescription
    var image by InterestPoints.vimage
}

class Draw(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<com.example.database.Draw>(Draws)
    var name by Draws.vname
    var creationDate by Draws.vcreationdate
    var lifetime by Draws.vlifetime
    var interestpoint by Draws.vinterestpoint
    var image by Draws.vimage
}

class Relation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<com.example.database.Relation>(Relations)
    var iduser by Relations.vidUser
    var idusercible by Relations.vidUserCible
    var follow by Relations.vfollow
}

class Noted(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<com.example.database.Noted>(Noteds)
    var iduser by Noteds.vidUser
    var iddraw by Noteds.vidDraw
    var note by Noteds.vnote
}

class Collaborated(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<com.example.database.Collaborated>(Collaborateds)
    var iduser by Collaborateds.vidUser
    var iddraw by Collaborateds.vidDraw
}

class Commented(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<com.example.database.Commented>(Commenteds)
    var iduser by Commenteds.vidUser
    var iddraw by Commenteds.vidDraw
    var date by Commenteds.vdate
    var msg by Commenteds.vmsg
}

class ActionDone(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<com.example.database.ActionDone>(ActionsDone)
    var iduser by ActionsDone.vidUser
    var iddraw by ActionsDone.vidDraw
    var report by ActionsDone.vreport
    var like by ActionsDone.vlike
    var creator by ActionsDone.vcreator
}

