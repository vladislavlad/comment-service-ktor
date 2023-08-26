package software.darkmatter.comment

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import software.darkmatter.comment.service.comment.CommentService

fun Application.configureRouting() {

    val commentService by inject<CommentService>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/comments") {
            call.respond(
                commentService.list()
            )
        }
    }
}
