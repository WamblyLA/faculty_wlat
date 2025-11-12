package ru.tbank.education.school.lesson1
fun isExisting(str: String): Boolean {
    for (k in OperationType.entries) {
        if (k.possible.any {it.lowercase() == (str.lowercase())}) {
            return true;
        }
    }
    return false;
}
fun preobrazovatel(str: String): String {
    when (str.lowercase()) {
        in listOf("add", "добавить") -> return "+"
        in listOf("subtract", "вычесть") -> return "-"
        in listOf("multiply", "умножить") -> return "*"
        in listOf("sin") -> return "sin"
        in listOf("cos") -> return "cos"
        in listOf("sqrt") -> return "sqrt"
        in listOf("divide", "разделить") -> return "/"
    }
    return str.lowercase();
}
fun priority(str: String): Int {
    when (str) {
        in listOf("+", "-") -> return 1
        in listOf("*", "/") -> return 2
    }
    return 0;
}
fun toOPZ(str: String): ArrayList<String> {
    var returning = ArrayList<String>();
    var stack = ArrayDeque<String>();
    var i = 0
    while (i < str.length) {
        val char = str[i];
        if (char.isWhitespace()) {
            i++
            continue;
        }
        if (char.isDigit() || char == '.') {
            var newNum = ""
            while (i < str.length && (str[i].isDigit() || str[i] == '.')) {
                newNum += str[i];
                i++
            }
            returning.add(newNum);
        } else if (char.isLetter()) {
            var newOper = ""
            while (i < str.length && str[i].isLetter()) {
                newOper += str[i];
                i++
            }
            if (isExisting(newOper)) {
                val preobr = preobrazovatel(newOper);
                while (!stack.isEmpty() && stack.last() != "(" && priority(stack.last()) >= priority(preobr)) {
                    returning.add(stack.removeLast());
                }
                stack.addLast(preobr);
            } else {
                return ArrayList<String>();
            }
        } else if (char == '(') {
            stack.addLast("(")
            i++;
        } else if (char == ')') {
            while (!stack.isEmpty() && stack.last() != "(") {
                returning.add(stack.removeLast());
            }
            if (!stack.isEmpty() && stack.last() == "(") {
                stack.removeLast();
            } else {
                return ArrayList<String>();
            }
            i++;
        } else {
            val procheck = char.toString();
            while (!stack.isEmpty() && stack.last() != "(" && priority(stack.last()) >= priority(procheck)) {
                returning.add(stack.removeLast());
            }
            stack.addLast(procheck);
            i++;
        }
    }
    while (!stack.isEmpty()) {
        returning.add(stack.removeLast());
    }
    return returning;
}
/*fun calculate(a: Double?, b: Double?, operation: OperationType = OperationType.ADD): Double? {
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
    return 0.0
}*/
fun main() {
    val arr = toOPZ("((1add2)* 3) вычесть1")
    if (arr.isEmpty()) {
        println("Кажется, в выражении что-то не так...")
    }
    for (k in arr){
        print(k)
        print(" ")
    }
}
/**
 * Функция вычисления выражения, представленного строкой
 * @return результат вычисления строки или null, если вычисление невозможно
 * @sample "5 * 2".calculate()*/


/*@Suppress("ReturnCount")
fun String.calculate(): Double? {
    return 2.0
}
*/