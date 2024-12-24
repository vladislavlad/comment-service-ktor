package software.darkmatter.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    authentication {
        jwt("auth-jwt") {
            val jwtAudience = this@configureSecurity.environment.config.property("jwt.audience").getString()
            // realm = this@configureSecurity.environment.config.property("jwt.realm").getString()

//            verifier(
//                JWT
//                    .require()
//                    .withAudience(jwtAudience)
//                    .withIssuer(this@configureSecurity.environment.config.property("jwt.issuer").getString())
//                    .build()
//            )

            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
