package com.example.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import com.example.stub.*

import com.example.classlib.*
import com.example.datacontract.toDTO
import com.google.gson.Gson

fun main() {
            val data = Stub();

            val app = Javalin.create().apply {
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json(Gson().toJson("notFound")) }
            }.start(1705)

            app.routes {

                //USERS

                get("/users") { ctx ->
                    ctx.json(toDTO(data.userHashMap))
                }
                get("/users/{user-id}") { ctx ->
                    val res = data.getUserById(ctx.pathParam("user-id").toInt())
                    if(res==null){
                        ctx.json(Gson().toJson("notFound"))
                    }
                    else{
                        ctx.json(toDTO(res))
                    }
                }
                get("/users/email/{content}"){ ctx ->
                    val res = data.getuserByEmail(ctx.pathParam("content").toString())
                    if(res==null){
                        ctx.json(Gson().toJson("notFound"))
                    }
                    else{
                        ctx.json(toDTO(res))
                    }
                }
                get("/users/idx/{idx}/{nb}") { ctx ->
                    ctx.json(
                        toDTO(
                            data.loadUsersIndex(
                                ctx.pathParam("idx").toInt(),
                                ctx.pathParam("nb").toInt()
                            )
                        )
                    )
                }
                get("/user/pwd/{login}/{pwd}"){ctx ->
                    val res = data.findUserByLogPswd(
                        ctx.pathParam("login").toString(),
                        ctx.pathParam("pwd").toString()
                    )
                    if(res==null){
                        ctx.json(Gson().toJson("notFound"))
                    }
                    else{
                        ctx.json(toDTO(res))
                    }
                }
                get("/loadData"){ctx ->
                    ctx.json(data.loadData())
                }
                post("/users") { ctx ->
                    val user = ctx.bodyAsClass<User>()
                    println(user)
                    data.createUser(
                        User(
                            id = 0,
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
                    data.updateUser(
                        id = ctx.pathParam("user-id").toInt(),
                        usr = user
                    )
                    ctx.status(204)
                }

                delete("/users/{user-id}") { ctx ->
                    data.deleteUser(ctx.pathParam("user-id").toInt())
                    ctx.status(204)
                }
/*
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
                Draw(
                    id =0,
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
*/ }
}

