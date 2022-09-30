import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main() {

    val userDao = UserDao()

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7070)

    app.routes {

        get("/users") { ctx ->
            ctx.json(userDao.users)
        }

        get("/users/{user-id}") { ctx ->
            ctx.json(userDao.findById(ctx.pathParam("user-id").toInt())!!)
        }

        post("/users") { ctx ->
            val user = ctx.bodyAsClass<User>()
            userDao.save(name = user.name, email = user.email)
            ctx.status(201)
        }

        put("/users/{user-id}") { ctx ->
            val user = ctx.bodyAsClass<User>()
            userDao.update(
                id = ctx.pathParam("user-id").toInt(),
                user = user
            )
            ctx.status(204)
        }

        delete("/users/{user-id}") { ctx ->
            userDao.delete(ctx.pathParam("user-id").toInt())
            ctx.status(204)
        }

    }

}
