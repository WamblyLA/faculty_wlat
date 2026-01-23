fun stringLectureExample() {
    // 1. Создание строк
    val s1 = """Hello"""
    val s2 = "World $s1"

    // строки immutable
    // s1[0] = 'h' // ❌ невозможно

    // 2. Конкатенация
    val concat = s1 + " " + s2
    println(concat) // Hello World

    println()

    // 3. Шаблоны строк (string templates)
    val year = 2024
    val template = "$s1 $s2, year = $year"
    println(template)

    val calc = "2 + 2 = ${2 + 2}"
    println(calc)

    println()

    // 4. Многострочные строки
    val multiline = """
        First line
        Second line
        Third line
    """.trimIndent()
    println(multiline)

    println()

    // 5. Длина, доступ по индексу
    println(s1.length)     // 5
    println(s1[1])         // 'e'

    println()

    // 6. Подстроки
    val text = "Kotlin is awesome"
    println(text.substring(0, 6))   // Kotlin
    println(text.substringAfter("is ")) // awesome

    println()

    // 7. Поиск
    println(text.contains("awesome")) // true
    println(text.startsWith("Kot"))   // true
    println(text.endsWith("me"))      // true
    println(text.indexOf("is"))       // 7

    println()

    // 8. Замена
    val replaced = text.replace("awesome", "great")
    println(text)
    println(replaced)

    println()

    // 9. Trim / strip
    val spaced = "   Kotlin   "
    println(spaced.trim())        // "Kotlin"
    println(spaced.trimStart())   // "Kotlin   "
    println(spaced.trimEnd())     // "   Kotlin"

    println()

    // 10. Split и join
    val csv = "one,two,three"
    val parts = csv.split(",")
    println(parts) // [one, two, three]

    val joined = parts.joinToString(" | ")
    println(joined) // one | two | three

    println()

    // 11. Регистры
    println("kotlin".uppercase()) // KOTLIN
    println("KOTLIN".lowercase()) //

    println()

    // 12. Проверки на пустоту
    val empty = ""
    val blank = "   "

    println(empty.isEmpty()) // true
    println(blank.isBlank()) // true

    println()

    // 13. Безопасная работа с null
    val nullable: String? = null
    println(nullable?.length) // null
    println(nullable ?: "default")

    println()

    // 14. Эффективная сборка строк
    val built = buildString {
        append("Line ")
        for (i in 1..3) {
            append(i).append(" ")
        }
    }
    println(built)
}

fun main() {
    stringLectureExample()
}