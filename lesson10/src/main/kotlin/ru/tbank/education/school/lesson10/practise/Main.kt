import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun main() {
    task1()
    println()
    task2()
    println()
    task3()
    task4()
    println()
    task5()
    println()
    task6()
    println()
    task7()
    println()
    task8()
    println()
}


/*
1) Строки + регулярные выражения
["Name: Ivan, score=17", ...]
Извлечь имя и score, собрать пары, вывести победителя.
*/
fun task1() {
    val lines = listOf(
        "Name: Ivan, score=17",
        "Name: Olga, score=23",
        "Name: Max, score=5"
    )

    val re = Regex("""^Name:\s*([A-Za-z]+)\s*,\s*score=(\d+)\s*$""")

    val pairs: List<Pair<String, Int>> = lines.mapNotNull { s ->
        val m = re.find(s) ?: return@mapNotNull null
        val name = m.groupValues[1]
        val score = m.groupValues[2].toInt()
        name to score
    }

    println("Task 1 pairs: $pairs")

    val best = pairs.maxByOrNull { it.second }
    if (best != null) {
        println("Task 1 best: ${best.first} (${best.second})")
    } else {
        println("Task 1: no valid lines")
    }
}

/*
2) Даты + коллекции
["2026-01-22", ...]
Преобразовать в даты, отсортировать, посчитать сколько в январе 2026.
*/
fun task2() {
    val dateStrings = listOf(
        "2026-01-22",
        "2026-02-01",
        "2025-12-31",
        "2026-01-05"
    )

    val fmt = DateTimeFormatter.ISO_LOCAL_DATE

    val dates = dateStrings.map { LocalDate.parse(it, fmt) }.sorted()

    println("Task 2 sorted dates: ${dates.joinToString { it.format(fmt) }}")

    val countJan2026 = dates.count { it.year == 2026 && it.month == Month.JANUARY }
    println("Task 2 count in Jan 2026: $countJan2026")
}

/*
3) Коллекции + строки
"apple orange apple banana orange apple"
Частоты слов, вывести слова с частотой > 1 по алфавиту.
*/
fun task3() {
    val text = "apple orange apple banana orange apple"

    val words = text.trim().split(Regex("""\s+""")).filter { it.isNotEmpty() }

    val freq = mutableMapOf<String, Int>()
    for (w in words) {
        freq[w] = (freq[w] ?: 0) + 1
    }

    println("Task 3 freq: $freq")

    val repeated = freq
        .filter { (_, c) -> c > 1 }
        .keys
        .sorted()

    println("Task 3 repeated words: ${repeated.joinToString(", ")}")
}
fun task4() {
    val list = listOf(
        "A-123",
        "B-7",
        "AA-12",
        "C-001",
        "D-99x",
        "Aadfsdfs",
        "124sd12"
    )
    var regex = Regex("""([A-Z])-(\d{1,3})""")
    val types = list.filter { regex.containsMatchIn(it) }
    println("Task 4 elem count: ${types.size}")
    println(types)
}
fun task5() {
    val list = listOf(
        " Hello              world ",
        "A B                                    C",
        " one"
    )
    val regex = Regex("""\s+""")
    val listNo = list.map { it.trim().replace(regex, " ") }
    println(listNo)
}
fun task6() {
    val list : List<Pair<String, String>> = listOf("2026-01-01" to "2026-01-10", "2025-12-31" to "2026-01-01", "2026-02-01" to "2026-01-22")
    val listDate = list.map {LocalDate.parse(it.first) to LocalDate.parse(it.second)}
    val ans = mutableListOf<Long>()
     for (paira in listDate) {
         ans.add(ChronoUnit.DAYS.between(paira.second, paira.first))
     }
    println(ans)
}
fun task7() {
    val list = listOf("math:Ivan", "bio:Olga", "math:Max", "bio:Ivan", "cs:Olga")
    val mapa = mutableMapOf<String, MutableList<String>>()
    for (str in list) {
        val newStr = str.split(":")
        mapa.getOrPut(newStr[0]) { mutableListOf() }.add(newStr[1])
    }
    println(mapa)
}
fun task8() {
    val list = listOf("Start at 2026/01/22 09:14", "No time here", "End: 22-01-2026 18:05", "Test: 2026-01-22 13:03")
    val regex = Regex("""\d{4}-\d{2}-\d{2} (\d{2}):(\d{2})""")
    val newList = list.mapNotNull { elem ->
        regex.find(elem)?.value
    }
    println(newList)
}
