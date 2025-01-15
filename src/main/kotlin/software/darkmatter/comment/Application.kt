package software.darkmatter.comment

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
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
        slf4jLogger()
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
        val url = environment.config.property("db.url").getString()
        val user = environment.config.property("db.user").getString()
        val password = environment.config.property("db.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}
