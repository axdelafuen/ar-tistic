package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDate
// object creation

object User : Table<User_>("t_User"){
    val username =  varchar("username").primaryKey().bindTo {it.username}
    val firstName = varchar("first_Name").bindTo {it.firstName}
    val lastName = varchar("last_Name").bindTo {it.lastName}
    val DoB = date("date_of_Birth").bindTo {it.DoB}
    val password = varchar("password").bindTo {it.password}
    val mail = varchar("email").bindTo {it.mail}
    val creation = date("date_of_Creation").bindTo {it.creation}
    val nbReport = int("number_of_Report").bindTo {it.nbReport}
    val score = int("global_Score").bindTo {it.score}
}

object Drawing: Table<Drawing_>("t_drawing"){
    val nbViews = int("nb_views").bindTo {it.nbViews}
    val creationDate = date("date_creation").bindTo {it.creationDate}
    val id = varchar("id").primaryKey().bindTo {it.id}
    val longevite = int("longevite").bindTo {it.longevite}
    val nom = varchar("name").bindTo {it.name}
    val InterestPointCreation = varchar("id_interest_point").references(InterestPoint) {it.pointOfInterest}
}

object Relation: Table<Relation_>("t_relation"){
    val idUser = varchar("id_user").primaryKey().references(User) {it.user}
    val idUserCible = varchar("id_user_cible").primaryKey().references(User) {it.userCible}
    val followCible = boolean("follow_cible").bindTo {it.followCible}
}

object InterestPoint: Table<InterestPoint_>("t_interest_point"){
    val altitude = float("altitude").bindTo {it.altitude}
    val longitude = float("longitude").bindTo {it.longitude}
    val desc = varchar("desc").bindTo {it.desc}
    val name = varchar("name").bindTo {it.name}
    val id = varchar("id").primaryKey().bindTo {it.id}
}

object Commented: Table<Commented_>("t_commented"){
    val idUser = varchar("id_user").primaryKey().references(User) {it.user}
    val drawing = varchar("drawing").primaryKey().references(Drawing) {it.drawing}
    val date = date("date").bindTo { it.date }
    val message = varchar("message").bindTo { it.msg }
}

object Noted: Table<Noted_>("t_noted"){
    val idUser = varchar("id_user").primaryKey().references(User) {it.user}
    val drawing = varchar("drawing").primaryKey().references(Drawing) {it.drawing}
    val note = float("note").bindTo { it.note }
}

object Collaborated: Table<Collaborated_>("t_collaborated"){
    val idUser = varchar("id_user").primaryKey().references(User) { it.user }
    val drawing = varchar("drawing").primaryKey().references(Drawing) {it.drawing}
    val dateModif = date("date_modif").bindTo { it.dateModif }
}

object ActionDone: Table<ActionDone_>("t_action_done"){
    val idUser = varchar("id_user").primaryKey().references(User) {it.user}
    val drawing = varchar("drawing").primaryKey().references(Drawing) {it.drawing}
    val report = boolean("report").bindTo { it.report }
    val like = boolean("like").bindTo { it.like }
    val creator = boolean("creator").bindTo { it.creator }
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
    var user: User_
    var userCible: User_
    val followCible: Boolean
}

interface InterestPoint_ : Entity<InterestPoint_> {
    var altitude: Float
    var longitude: Float
    val id: String
    var desc: String
    var name: String
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
    val user: User_
    val drawing: Drawing_
    val date: LocalDate
    val msg: String
}

interface Noted_ : Entity<Noted_> {
    val user: User_
    val drawing: Drawing_
    val note: Float
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
