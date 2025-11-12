package ru.tbank.education.school.lesson1

import java.util.Objects

fun main() {
    print("Hello World!")
    println("Hello World!")

    var a: Short? = 123
    val b = "s"
    val c = 's'
    val d = 123
    val e = true
    val f = """
        # Организация проекта
    """.trimIndent()
    val g = "hello $d world"
    println(g)
    var h = 1
    h = 2

    if (a != null) {
        print(a)
    } else {
    }

    h = if (a != null) 123 else 333

    val i = if (a != null) {
        123
    } else {
        333
    }

    val j = when (a != null) {
        true -> 123
        false -> 333
    }

    val array = arrayOf(1, 2, 3)

    val doubleDimensionArray =
        Array(2) { Array(2) { 0 } }

    doubleDimensionArray[0][0]

    println("\n")

    for (i in 0 .. 6 step 2) {
        println(i)
    }

    for (i in 6 downTo 0 step 2) {
        println(i)
    }

    for (elem in array) {
        println(elem)
    }

    throw IllegalArgumentException("")

}
