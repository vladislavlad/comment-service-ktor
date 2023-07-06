package software.darkmatter.comment

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
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
}
