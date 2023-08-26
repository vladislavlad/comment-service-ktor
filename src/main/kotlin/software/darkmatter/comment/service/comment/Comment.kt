package software.darkmatter.comment.service.comment

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Long,
    val text: String,
)
