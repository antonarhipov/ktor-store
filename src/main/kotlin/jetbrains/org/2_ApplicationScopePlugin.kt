package jetbrains.org

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.reflect.KClass

// Using application scope plugin to register the endpoints by iterating the list of data classes
// This is probably not the best option as it installs routes which should be part of routing functionality
// It's probably better to define the top-level routes explicitly and then generate the REST methods for the specific route
fun Application.applicationScopePlugin() {
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

            }
        }
    }

