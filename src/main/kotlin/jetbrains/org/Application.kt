package jetbrains.org

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


data class Item1(val id: String, val name: String, val price: Double, val description: String?)
data class Item2(val id: String, val name: String, val price: Double, val description: String?)
data class Item3(val id: String, val name: String, val price: Double, val description: String?)


val data = listOf(
    Item1("1", "apples", 10.0, "Apples from Poland"),
    Item1("2", "oranges", 20.0, "Oranges from Spain"),
    Item2("3", "potato", 0.5, "Potato from Belarus"),
    Item2("4", "carrots", 1.5, "Carrots from Italy"),
    Item3("5", "strawberries", 20.5, "Carrots from UK")
)
