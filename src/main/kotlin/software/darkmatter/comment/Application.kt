package software.darkmatter.comment

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import software.darkmatter.comment.service.comment.CommentService
import software.darkmatter.comment.service.comment.CommentServiceImpl
import software.darkmatter.plugins.configureDatabases
import software.darkmatter.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
//    configureSecurity()
    configureRouting()
    install(CallLogging)

    install(Koin) {
        modules(commentModule())
    }
}

fun commentModule(): Module {
    return module {
        singleOf<CommentService>(::CommentServiceImpl)
    }
}
