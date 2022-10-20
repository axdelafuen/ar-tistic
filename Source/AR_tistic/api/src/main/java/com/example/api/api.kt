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
            ctx.json(userDao.getUserWithIndex(ctx.pathParam("idx").toInt(),ctx.pathParam("nb").toInt())!!)
        }
        post("/users") { ctx ->
            val user = ctx.bodyAsClass<User>()
            println(user)
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

        // DRAWS

        get("/draws") { ctx ->
            ctx.json(drawDao.getDraws()!!)
        }
        get("/draws/{draw-id}") { ctx ->
            ctx.json(drawDao.getDrawsById(ctx.pathParam("draw-id").toInt())!!)
        }
        get("/draws/idx/{idx}/{nb}"){ctx->
            ctx.json(drawDao.getDrawWithIndex(ctx.pathParam("idx").toInt(),ctx.pathParam("nb").toInt())!!)
        }
        post("/draws") { ctx ->
            val draw = ctx.bodyAsClass<Draw>()
            drawDao.createDraw(
                Draw(id=0,
                    name = draw.name,
                    image = draw.image,
                    interestPoint = draw.interestPoint,
                    creationDate = draw.creationDate,
                    lifeTime = draw.lifeTime,
                    authors = draw.authors,
                    nbView = draw.nbView,
                    nbReport = draw.nbReport
                )
            )
            ctx.status(201)
        }
        put("/draws/{draw-id}") { ctx ->
            val draw = ctx.bodyAsClass<Draw>()
            drawDao.updateDraw(
                id = ctx.pathParam("draw-id").toInt(),
                draw = draw
            )
            ctx.status(204)
        }

        delete("/draws/{draw-id}") { ctx ->
            drawDao.deleteDraw(ctx.pathParam("draw-id").toInt())
            ctx.status(204)
        }

        // INTEREST POINTS

        get("/intPoints") { ctx ->
            ctx.json(intPointDao.getInterestPoint()!!)
        }
        get("/intPoints/{intPoint-id}") { ctx ->
            ctx.json(intPointDao.getInterestPointById(ctx.pathParam("intPoint-id").toInt())!!)
        }
        get("/intPoints/idx/{idx}/{nb}"){ctx->
            ctx.json(intPointDao.getInterestPointWithIndex(ctx.pathParam("idx").toInt(),ctx.pathParam("nb").toInt())!!)
        }
        post("/intPoints") { ctx ->
            val intPoint = ctx.bodyAsClass<InterestPoint>()
            intPointDao.createInterestPoint(
                InterestPoint(id=0,
                    name=intPoint.name,
                    desc=intPoint.desc,
                    latitude = intPoint.latitude,
                    longitude = intPoint.longitude,
                    picture = intPoint.picture
                )
            )
            ctx.status(201)
        }
        put("/intPoints/{intPoint-id}") { ctx ->
            val intPoint = ctx.bodyAsClass<InterestPoint>()
            intPointDao.updateInterestPoint(
                id = ctx.pathParam("intPoint-id").toInt(),
                intPoint=intPoint
            )
            ctx.status(204)
        }

        delete("/intPoints/{intPoint-id}") { ctx ->
            intPointDao.deleteInterestPoint(ctx.pathParam("intPoint-id").toInt())
            ctx.status(204)
        }

    }
}

