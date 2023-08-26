package software.darkmatter.comment.service.comment

interface CommentService {

    suspend fun list(): List<Comment>
}
