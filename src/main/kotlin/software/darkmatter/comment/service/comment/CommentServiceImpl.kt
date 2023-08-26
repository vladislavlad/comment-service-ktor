package software.darkmatter.comment.service.comment

class CommentServiceImpl : CommentService {

    override suspend fun list(): List<Comment> = listOf(Comment(1, "Hello World!"))
}
