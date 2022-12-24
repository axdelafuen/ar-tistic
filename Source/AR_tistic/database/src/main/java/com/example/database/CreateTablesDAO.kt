package com.example.database

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalTime

fun createTable() {
    Database.connect(
        url = "jdbc:mysql://"+System.getenv("DB_SERVER")+"/"+System.getenv("DB_DATABASE"),
        user = "root",
        password = System.getenv("DB_ROOT_PASSWORD")
        /*url = "jdbc:mysql://localhost:3306/sqlsaetest",
        user = "root",
        password = "root1234"
        test*/
    )

    transaction {
        addLogger(StdOutSqlLogger)

        // DROP TABLES

        SchemaUtils.drop(t_Collaborateds)
        SchemaUtils.drop(t_ActionsDone)
        SchemaUtils.drop(t_Relations)
        SchemaUtils.drop(t_Commenteds)
        SchemaUtils.drop(t_Noteds)
        SchemaUtils.drop(t_Users)
        SchemaUtils.drop(t_Draws)
        SchemaUtils.drop(t_InterestPoints)

        //  CREATE TABLES

        SchemaUtils.create(t_Users)
        SchemaUtils.create(t_Draws)
        SchemaUtils.create(t_Noteds)
        SchemaUtils.create(t_InterestPoints)
        SchemaUtils.create(t_Commenteds)
        SchemaUtils.create(t_Relations)
        SchemaUtils.create(t_Collaborateds)
        SchemaUtils.create(t_ActionsDone)


    }


}

object t_Users: IntIdTable(){
    val vname: Column<String> = varchar("username",50)
    val vemail: Column<String> = varchar("email",100)
    val vprofilePicture: Column<String> = largeText("profilePicture")
    val vpassword: Column<String> = varchar("password", 50)
    val vbirthdate: Column<java.time.LocalDate> = date("date")
}

object t_Draws: IntIdTable(){
    val vname: Column<String> = varchar("imageName",50)
    val vimage: Column<String> = largeText("image",100)
    val vlifetime: Column<LocalTime> = time("password")
    val vcreationdate: Column<java.time.LocalDate> = date("date")
    val vnbviews :  Column<Int> = integer("nbViews")
}

object t_InterestPoints: IntIdTable(){
    val vname: Column<String> = varchar("interestPointName",50)
    val vdescription: Column<String> = varchar("description",100)
    val vlatitude: Column<Double> = double("latitude")
    val vlongitude: Column<Double> = double("longitude")
    val vimage: Column<String> = largeText("interestPointImage",100)
}

object t_CreatedsOn: IntIdTable(){
    val vidDraw = reference("idDraw",t_Draws)
    val vInterestPoint = reference("idInterestPoint", t_InterestPoints)
}

object t_Relations: IntIdTable(){
    val vidUser = reference("iduser",t_Users)
    val vidUserCible = reference("idusercible", t_Users)
    val vfollow: Column<Boolean> = bool("follow")
}

object t_ActionsDone: IntIdTable(){
    val vidUser = reference("iduser", t_Users)
    val vidDraw = reference("iddraw", t_Draws)
    val vreport: Column<Boolean> = bool("report")
    val vlike: Column<Boolean> = bool("like")
    val vcreator: Column<Boolean> = bool("creator")
}

object t_Noteds: IntIdTable() {
    val vidUser = reference("iduser", t_Users)
    val vidDraw = reference("iddraw", t_Draws)
    val vnote: Column<Float> = float("note")
}

object t_Collaborateds: IntIdTable() {
    val vidUser= reference("iduser", t_Users)
    val vidDraw = reference("iddraw", t_Draws)
}

object t_Commenteds: IntIdTable(){
    val vidUser = reference("iduser", t_Users)
    val vidDraw = reference("iddraw", t_Draws)
    val vdate: Column<java.time.LocalDate> = date("dateCommentaire")
    val vmsg: Column<String> = varchar("commentaire", 250)
}

class t_User(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<t_User>(t_Users)
    var name by t_Users.vname
    var email by t_Users.vemail
    var profilePicture by t_Users.vprofilePicture
    var password by t_Users.vpassword
    var birthdate by t_Users.vbirthdate
}

class t_InterestPoint(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<t_InterestPoint>(t_InterestPoints)
    var name by t_InterestPoints.vname
    var latitude by t_InterestPoints.vlatitude
    var longitude by t_InterestPoints.vlongitude
    var description by t_InterestPoints.vdescription
    var image by t_InterestPoints.vimage
}

class t_Draw(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<t_Draw>(t_Draws)
    var name by t_Draws.vname
    var creationDate by t_Draws.vcreationdate
    var lifetime by t_Draws.vlifetime
    var image by t_Draws.vimage
    var nbViews by t_Draws.vnbviews
}

class t_Relation(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<t_Relation>(t_Relations)
    var iduser by t_Relations.vidUser
    var idusercible by t_Relations.vidUserCible
    var follow by t_Relations.vfollow
}

class t_Noted(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<t_Noted>(t_Noteds)
    var iduser by t_Noteds.vidUser
    var iddraw by t_Noteds.vidDraw
    var note by t_Noteds.vnote
}

class t_Collaborated(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<t_Collaborated>(t_Collaborateds)
    var iduser by t_Collaborateds.vidUser
    var iddraw by t_Collaborateds.vidDraw
}

class t_Commented(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<t_Commented>(t_Commenteds)
    var iduser by t_Commenteds.vidUser
    var iddraw by t_Commenteds.vidDraw
    var date by t_Commenteds.vdate
    var msg by t_Commenteds.vmsg
}

class t_ActionDone(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<t_ActionDone>(t_ActionsDone)
    var iduser by t_ActionsDone.vidUser
    var iddraw by t_ActionsDone.vidDraw
    var report by t_ActionsDone.vreport
    var like by t_ActionsDone.vlike
    var creator by t_ActionsDone.vcreator
}

class t_CreatedOn(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<t_CreatedOn>(t_CreatedsOn)
    var iddraw by t_CreatedsOn.vidDraw
    var idinterestpoint by t_CreatedsOn.vInterestPoint
}

