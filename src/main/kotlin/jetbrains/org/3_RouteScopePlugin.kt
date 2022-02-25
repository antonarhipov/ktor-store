package jetbrains.org

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

//experimenting with route scope plugins
fun Application.routeScopePlugin() {
    routing {

        route("/items") {
            install(ValidationPlugin) {
                validateRequest {
                    require(it.request.header("x-blah-blah") != null)
                }

                validate<Item1> {
                    check(it.id.isNotEmpty())
                }
            }

            //should be post
            get("/") {
                call.respondText(data.filter {
                    it::class.simpleName?.lowercase() == "path"
                }.toString())
            }
        }
    }
}

class ValidationConfig {
    internal val bodyValidator = mutableListOf<(Any) -> Unit>()

    inline fun <reified T : Any> validate(crossinline block: (T) -> Unit) {
        validateBody {
            if (it is T) {
                block(it) //crossinline or noinline
            }
        }
    }

    inline fun <reified T : Any> validateNoinline(noinline block: (T) -> Unit) {
        validateBody {
            if (it is T) {
                block(it) //crossinline or noinline
            }
        }
    }


    fun validateBody(block: (Any) -> Unit) {
        bodyValidator += block
    }

    fun validateRequest(block: (ApplicationCall) -> Unit) {

    }
}


//explaining crossinline vs noinline
fun ValidationConfig.crossinlineVSnoinline() {

    //CROSSINLINE
    validate<String> { println("foo") }

    // will be replaced
    validateBody {
        if (it is String) {
            println("foo")
        }
    }

    // NOINLINE
    validateNoinline<String> { println("foo") }

    val myBlock = { it: String -> println("foo") }
    validateBody {
        if (it is String) {
            myBlock(it)
        }
    }
}

val ValidationPlugin = createRouteScopedPlugin("RouteScopeControllers", { ValidationConfig() }) {
    val bodyValidator = pluginConfig.bodyValidator
    require(bodyValidator.isNotEmpty())

    onCallReceive { call, body ->
        bodyValidator.forEach {
            it(body)
        }
    }

    onCall { call ->
        println(call.request.uri)
    }
}

