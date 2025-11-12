package ru.tbank.education.school.lesson1

/**
 * Сумма четных чисел.
 */
fun sumEvenNumbers(numbers: Array<Int>) =
    numbers.filter { it % 2 == 0 }.sum()

fun main() {
    val numbers = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(sumEvenNumbers(numbers))
}
