package software.darkmatter.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val jwkProvider = JwkProviderBuilder(issuer)
//        .cached(10, 24, TimeUnit.HOURS)
//        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    authentication {
        jwt("auth-jwt") {
//            realm = jwtRealm
            verifier(jwkProvider, issuer) {
                acceptLeeway(1)
            }
            validate { credential ->
                if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
