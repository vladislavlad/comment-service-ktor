package software.darkmatter.comment.service.comment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import software.darkmatter.comment.service.api.dto.CommentCreateDto
import software.darkmatter.comment.service.api.dto.CommentUpdateDto
import java.sql.Connection
import java.sql.Statement

class CommentServiceImpl(private val connection: Connection) : CommentService {

    companion object {
        private const val SELECT_CITY_BY_ID = "select id, text, author, target, target_id, reply_to from comments where id = ?"
        private const val SELECT_LIST = "select id, text, author, target, target_id, reply_to from comments"
        private const val INSERT_CITY = "insert into comments (text, author, target) values (?, ?, ?)"
        private const val UPDATE_CITY = "update comments set text = ? where id = ?"
        private const val DELETE_CITY = "delete from comments where id = ?"
    }

    override suspend fun getList(): List<Comment> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_LIST)
        val rs = statement.executeQuery()

        val list = ArrayList<Comment>()
        while (rs.next()) {
            list.add(
                Comment(
                    id = rs.getLong("id"),
                    text = rs.getString("text"),
                    author = rs.getString("author"),
                    target = rs.getString("target"),
                    targetId = rs.getLong("target_id"),
                    replyTo = rs.getLong("reply_to"),
                )
            )
        }

        list
    }

    override suspend fun getById(id: Int): Comment = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_CITY_BY_ID)
        statement.setInt(1, id)
        val rs = statement.executeQuery()

        if (rs.next()) {
            Comment(
                id = rs.getLong("id"),
                text = rs.getString("text"),
                author = rs.getString("author"),
                target = rs.getString("target"),
                targetId = rs.getLong("target_id"),
                replyTo = rs.getLong("reply_to"),
            )
        } else {
            throw Exception("Record not found")
        }
    }

    override suspend fun create(dto: CommentCreateDto): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_CITY, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, dto.text)
        statement.setString(2, dto.author)
        statement.setString(3, dto.target)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted Comment")
        }
    }

    override suspend fun update(id: Int, dto: CommentUpdateDto) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_CITY)
        statement.setString(1, dto.text)
        statement.setInt(2, id)
        statement.executeUpdate()
    }

    override suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_CITY)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}
