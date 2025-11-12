package ru.tbank.education.school.lesson1

/**
 * Список операций.
 */
enum class OperationType(val possible: List<String>) {
    ADD(listOf("добавить", "add", "+")),
    DIVIDE(listOf("разделить", "divide", "/")),
    MULTIPLY(listOf("multiply", "*", "умножить")),
    SUBTRACT(listOf("subtract", "-", "вычесть")),
    SIN(listOf("sin")),
    COS(listOf("cos")),
    SQRT(listOf("sqrt")),
}
