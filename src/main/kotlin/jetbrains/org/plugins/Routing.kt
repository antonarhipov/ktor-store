package jetbrains.org.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.http.content.*
import io.ktor.server.webjars.*
import java.time.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {


    install(Webjars) {
        path = "/webjars" //defaults to /webjars
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        install(StatusPages) {
            exception<AuthenticationException> { call, cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
    }
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
