package jetbrains.org

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.reflect.KClass

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

val data = listOf(
    Item1("1", "apples", 10.0, "Apples from Poland"),
    Item1("2", "oranges", 20.0, "Oranges from Spain"),
    Item2("3", "potato", 0.5, "Potato from Belarus"),
    Item2("4", "carrots", 1.5, "Carrots from Italy"),
    Item3("5", "strawberries", 20.5, "Carrots from UK")
)

fun Application.module() {
    install(DataControllers) {
        classes = listOf(Item1::class, Item2::class)
    }
}

class DataControllersConfig {
    var classes: List<KClass<out Any>> = mutableListOf()
}

val DataControllers: ApplicationPlugin<Application, DataControllersConfig, PluginInstance> =
    createApplicationPlugin("DataControllers", { DataControllersConfig() }) {
        application.routing {
            println("registering endpoints: " + pluginConfig.classes)
            for (c in pluginConfig.classes) {
                val path: String =
                    c.simpleName?.lowercase() ?: throw IllegalArgumentException("Class should have a name")

                println("Endpoint for $c is http://0.0.0.0:8080/${path}")
                get("/$path") {
                    call.respondText(data.filter {
                        it::class.simpleName?.lowercase() == path
                    }.toString())
                }

                post("/$path") {
                    TODO()
                }

                put("/$path") {
                    TODO()
                }

                delete("/$path") {
                    TODO()
                }

                // --------------------------------------

                head("/$path") {
                    TODO()
                }
                options("/$path") {
                    TODO()
                }


            }
        }
    }


data class Item1(val id: String, val name: String, val price: Double, val description: String?)
data class Item2(val id: String, val name: String, val price: Double, val description: String?)
data class Item3(val id: String, val name: String, val price: Double, val description: String?)
