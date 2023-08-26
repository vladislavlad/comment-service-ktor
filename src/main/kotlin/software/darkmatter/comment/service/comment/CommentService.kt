package software.darkmatter.comment.service.comment

import software.darkmatter.comment.service.comment.Comment

interface CommentService {

    suspend fun list(): List<Comment>
}
