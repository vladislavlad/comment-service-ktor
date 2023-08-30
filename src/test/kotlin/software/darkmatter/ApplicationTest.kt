package software.darkmatter

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import io.ktor.util.*
import software.darkmatter.comment.service.api.configureRouting

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
            attributes.put(AttributeKey("testMode"), true)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
