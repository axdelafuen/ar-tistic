package com.example.database

import com.example.classlib.Date
import com.example.database.Collaborateds.references
import com.example.database.Noteds.references
import com.sun.xml.internal.stream.Entity.InternalEntity
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalTime
import java.time.temporal.Temporal
import javax.swing.text.html.parser.Entity

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
    val vprofilePicture: Column<String> = varchar("profilePicture",100)
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
    val date: Column<java.time.LocalDate> = date("dateCommentaire")
    val msg: Column<String> = varchar("commentaire", 250)
}

class User(id: EntityID<Int>) : IntEntity(id){

}
