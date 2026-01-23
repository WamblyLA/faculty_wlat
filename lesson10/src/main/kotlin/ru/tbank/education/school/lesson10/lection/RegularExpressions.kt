import java.util.regex.Pattern

fun basicExample() {
    val helloWorld = "Hello World!"
    val welcomeToJava = "Welcome to Java!"

    val regex = Pattern.compile("^.{5}$")

    val helloWorldMatcher = regex.matcher(helloWorld)
    println("Find in helloWorld result: ${helloWorldMatcher.find()}") // true

    val welcomeToJavaMatcher = regex.matcher(welcomeToJava)
    println("Find in welcomeToJava result: ${welcomeToJavaMatcher.find()}") // false

    val justWorld = "World"
    val justWorldMatcher = regex.matcher(justWorld)
    println("Matches in justWorld result: ${justWorldMatcher.matches()}") // true

    println(
        "Matches in helloWorld result: ${regex.matcher(helloWorld).matches()}"
    ) // false
}

fun main() {
    basicExample()
}