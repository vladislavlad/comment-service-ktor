package software.darkmatter.comment.service.comment

import software.darkmatter.comment.service.api.dto.CommentCreateDto
import software.darkmatter.comment.service.api.dto.CommentUpdateDto

interface CommentService {

    suspend fun getList(): List<Comment>

    suspend fun getById(id: Int): Comment

    suspend fun create(dto: CommentCreateDto): Int

    suspend fun update(id: Int, dto: CommentUpdateDto): Int

    suspend fun delete(id: Int): Int
}
