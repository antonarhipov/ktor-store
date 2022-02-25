package jetbrains.org

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.http.content.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import io.ktor.server.webjars.*
import java.time.*
import io.ktor.server.html.*
import kotlinx.html.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import jetbrains.org.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() {
        testApplication {

        }
    }
}