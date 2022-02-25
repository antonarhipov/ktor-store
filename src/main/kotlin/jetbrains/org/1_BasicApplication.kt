package jetbrains.org

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

// no plugins, we can just generate the endpoints by iterting over the list of data classes
fun Application.basicApplication() {
    routing {
        for (c in listOf(Item1::class, Item2::class)) {
            val path: String = c.simpleName?.lowercase() ?: throw IllegalArgumentException("Class should have a name")

            println("Endpoint for $c is http://0.0.0.0:8080/${path}")
            get("/$path") {
                call.respondText(data.filter {
                    it::class.simpleName?.lowercase() == path
                }.toString())
            }
        }
    }
}