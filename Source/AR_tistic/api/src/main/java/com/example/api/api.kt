package com.example.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

import com.example.classlib.*

fun main() {

    val userDao = UserDao()
    val drawDao = DrawDao()
    val intPointDao = InterestPointDao()

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7070)

    app.routes {

        //USERS

        get("/users") { ctx ->
            ctx.json(userDao.getUsers()!!)
        }
        get("/users/{user-id}") { ctx ->
            ctx.json(userDao.getUserById(ctx.pathParam("user-id").toInt())!!)
        }
        get("/users/idx/{idx}/{nb}"){ctx->
            ctx.json(userDao.getUserWithIdex(ctx.pathParam("idx").toInt(),ctx.pathParam("nb").toInt())!!)
        }
        post("/users") { ctx ->
            val user = ctx.bodyAsClass<User>()
            userDao.createUser(
                User(id=0,
                name = user.name,
                profilePicture = user.profilePicture,
                email = user.email,
                password = user.password,
                birthDate = user.birthDate,
                subscribes = user.subscribes,
                nbReport = user.nbReport
                )
            )
            ctx.status(201)
        }
        put("/users/{user-id}") { ctx ->
            val user = ctx.bodyAsClass<User>()
            userDao.updateUser(
                id = ctx.pathParam("user-id").toInt(),
                user = user
            )
            ctx.status(204)
        }

        delete("/users/{user-id}") { ctx ->
            userDao.deleteUser(ctx.pathParam("user-id").toInt())
            ctx.status(204)
        }

        // INTEREST POINTS

        get("/intPoints") { ctx ->
            ctx.json(intPointDao.intPoints)
        }
        get("/intPoints/{intPoint-id}") { ctx ->
            ctx.json(intPointDao.findById(ctx.pathParam("intPoint-id").toInt())!!)
        }

        // DRAWS

        get("/draws"){ctx->
            ctx.json(drawDao.draws)
        }
        get("/draws/{draw-id}"){ctx->
            ctx.json(drawDao.findById(ctx.pathParam("draw-id").toInt())!!)
        }

    }
}

