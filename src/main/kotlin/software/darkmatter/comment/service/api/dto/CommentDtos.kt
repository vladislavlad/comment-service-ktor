package software.darkmatter.comment.service.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentCreateDto(
    val text: String,
    val author: String,
    val target: String,
)

@Serializable
data class CommentUpdateDto(
    val text: String,
)
