package software.darkmatter.comment.service.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import software.darkmatter.comment.service.api.dto.CommentCreateDto
import software.darkmatter.comment.service.api.dto.CommentUpdateDto
import software.darkmatter.comment.service.comment.CommentService

fun Application.configureCommentRouting() {

    val commentService by inject<CommentService>()

    routing {

        get("/comments") {
            try {
                val comments = commentService.getList()
                call.respond(HttpStatusCode.OK, comments)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/comments/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid 'id'")
            try {
                val comment = commentService.getById(id)
                call.respond(HttpStatusCode.OK, comment)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/comments") {
            val body = call.receive<CommentCreateDto>()
            val id = commentService.create(body)
            call.respond(HttpStatusCode.Created, id)
        }

        put("/comments/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid 'id'")
            val body = call.receive<CommentUpdateDto>()
            commentService.update(id, body)
            call.respond(HttpStatusCode.OK)
        }

        delete("/comments/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid 'id'")
            commentService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

