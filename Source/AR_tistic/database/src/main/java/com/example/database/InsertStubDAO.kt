package com.example.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main(){
    Database.connect(
        url = "jdbc:mysql://localhost:3306/sqlsaetest",
        user = "root",
        password = "root1234"
    )

    transaction {
        addLogger(StdOutSqlLogger)


    }
}