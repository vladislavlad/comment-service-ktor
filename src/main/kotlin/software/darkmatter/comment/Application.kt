package software.darkmatter.comment

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import software.darkmatter.comment.service.api.configureCommentRouting
import software.darkmatter.comment.service.api.configureRouting
import software.darkmatter.comment.service.comment.CommentService
import software.darkmatter.comment.service.comment.CommentServiceImpl
import software.darkmatter.plugins.configureSerialization
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
//    configureSecurity()
    configureRouting()
    configureCommentRouting()
    install(CallLogging)

    val db = connectToPostgres()
    install(Koin) {
        modules(commentModule(db))
    }
}

fun commentModule(db: Connection): Module {
    return module {
        singleOf<CommentService>({ CommentServiceImpl(db) })
    }
}

fun Application.connectToPostgres(embedded: Boolean = false): Connection {
    Class.forName("org.postgresql.Driver")
    return if (embedded) {
        DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val config = ConfigFactory.load()

        val url = config.getString("db.url")
        val user = config.getString("db.user")
        val password = config.getString("db.password")

        DriverManager.getConnection(url, user, password)
    }
}
