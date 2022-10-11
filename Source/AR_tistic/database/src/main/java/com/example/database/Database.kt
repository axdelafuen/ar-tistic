package com.example.database
import com.example.database.Collaborated.primaryKey
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDate

// object creation

object User : Table<Nothing>("t_User"){
    val username =  varchar("username").primaryKey()
    val firstName = varchar("first_Name")
    val lastName = varchar("last_Name")
    val DoB = date("date_of_Birth")
    val password = varchar("password")
    val mail = varchar("email")
    val creation = date("date_of_Creation")
    val nbReport = int("number_of_Report")
    val score = int("global_Score")
}

object Drawing: Table<Nothing>("t_drawing"){
    val nombreVue = int("nb_views")
    val dateCreation = date("date_creation")
    val id = varchar("id").primaryKey()
    val longevite = int("longevite")
    val nom = varchar("name")
    val PointInteretCreation = varchar("id_point_interet")
}

object Relation: Table<Nothing>("t_relation"){
    val idUser = varchar("id_user").primaryKey()
    val idUserCible = varchar("id_user_cible").primaryKey()
    val followCible = boolean("follow_cible")
}

object InterestPoint: Table<Nothing>("t_interest_point"){
    val altitude = float("altitude")
    val longitude = float("longitude")
    val desc = varchar("desc")
    val name = varchar("name")
    val id = varchar("id").primaryKey()
}

object Commented: Table<Nothing>("t_commented"){
    val idUser = varchar("id_user").primaryKey()
    val drawing = varchar("drawing").primaryKey()
    val date = date("date")
    val message = varchar("message")
}

object Noted: Table<Nothing>("t_noted"){
    val idUser = varchar("id_user").primaryKey()
    val drawing = varchar("drawing").primaryKey()
    val note = float("note")
}

object Collaborated: Table<Nothing>("t_collaborated"){
    val idUser = varchar("id_user").primaryKey()
    val drawing = varchar("drawing").primaryKey()
    val dateModif = date("date_modif")
}

object ActionDone: Table<Nothing>("t_action_done"){
    val idUser = varchar("id_user").primaryKey()
    val drawing = varchar("drawing").primaryKey()
    val report = boolean("report")
    val like = boolean("like")
    val creator = boolean("creator")
}



// interfaces

interface User_ : Entity<User_> {
    var username: String
    var firstName: String
    var lastName: String
    var DoB: LocalDate
    val password: String
    val mail: String
    var creation: LocalDate
    val nbReport: Int
    val score: Int
}

interface Relation_ : Entity<Relation_> {
    var user: User
    var userCible: User
    val followCible: Boolean
}

interface InterestPoint_ : Entity<InterestPoint_> {
    var altitude: Float
    var longitude: Float
    val id: String
    var desc: String
    var nom: String
}

interface Drawing_ : Entity<Drawing_> {
    var nbViews: Int
    val creationDate: LocalDate
    val id: String
    var longevite: Int
    val name: String
    val pointOfInterest: InterestPoint_
}

interface Commented_ : Entity<Commented_> {
    val user: User
    val drawing: Drawing_
    val date: LocalDate
    val msg: String
}

interface Noted_ : Entity<Noted_> {
    val user: User_
    val dessin: Drawing_
    val note: Int
}

interface Collaborated_ : Entity<Collaborated_> {
    val user: User_
    val drawing: Drawing_
    var dateModif: LocalDate
}

interface ActionDone_ : Entity<ActionDone_> {
    val user: User_
    var drawing: Drawing_
    val report: Boolean
    val creator: Boolean
    val like: Boolean
}

// Entity Create

val user_ = Entity.create<User_>()
val drawing_ = Entity.create<Drawing_>()
val interestPoint_ = Entity.create<InterestPoint_>()
val commented_ = Entity.create<Commented_>()
val collaborated_ = Entity.create<Collaborated_>()
val actionDone_ = Entity.create<ActionDone_>()
val noted_ = Entity.create<Noted_>()
val relation_ = Entity.create<Relation_>()