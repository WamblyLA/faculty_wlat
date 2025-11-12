package ru.tbank.education.school.lesson1

fun preobr(a: String): OperationType {
    if (a == "+") {
        return OperationType.ADD;
    } else if (a == "-") {
        return OperationType.SUBTRACT;
    } else if (a == "*") {
        return OperationType.MULTIPLY;
    } else if (a == "/") {
        return OperationType.DIVIDE;
    }
    return OperationType.ADD;
}
fun calculate(a: Double?, b: Double?, operation: OperationType = OperationType.ADD): Double? {
    if (a == null || b == null) {
        return null
    }
    when (operation) {
        OperationType.ADD-> {
            println(a+b)
            return a + b
        }
        OperationType.SUBTRACT -> {
            println(a - b)
            return a - b
        }

        OperationType.DIVIDE -> {
            if (b == 0.0) {
                println(null)
                return null
            } else {
                println(a/b)
                return a/b
            }
        }
        OperationType.MULTIPLY -> {
            println(a * b)
            return a * b
        }
    }

}
fun main() {
    val a = 132.0
    val b = 66.0
    calculate(a,b,preobr("*"))
}
/**
 * Функция вычисления выражения, представленного строкой
 * @return результат вычисления строки или null, если вычисление невозможно
 * @sample "5 * 2".calculate()*/


@Suppress("ReturnCount")
fun String.calculate(): Double? {
    return 2.0
}
