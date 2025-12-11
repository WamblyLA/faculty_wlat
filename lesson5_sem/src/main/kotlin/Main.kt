package org.example
fun main() {
    // Исходный список продуктов
    val products = listOf("Молоко", "Хлеб", "Сахар", "Сыр", "Масло", "Колбаса", "Сметана", "Яблоки")
    // todo 1. Проверка наличия "Хлеб" в коллекции
    println("-----------------------------")
    if ("Хлеб" in products) {
        println("Yes")
    } else {
        println("No")
    }
    println("-----------------------------")
    // todo 2. Сортировка по алфавиту и вывод
    val sorted = products.sorted()
    println(sorted)
    println("-----------------------------")
    // todo 3. Вывод только продуктов, начинающихся на букву "С"
    val ski = products.filter {it.startsWith("С", ignoreCase = true)}
    println(ski)
}