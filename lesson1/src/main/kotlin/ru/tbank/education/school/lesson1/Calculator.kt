package ru.tbank.education.school.lesson1
import kotlin.math.*
fun isExisting(str: String): Boolean {
    for (now in OperationType.entries) {
        if (now.possible.any {it.lowercase() == (str.lowercase())}) {
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
fun anotherPreobrazovatel(str: String): OperationType {
    when (str.lowercase()) {
        in listOf("+") -> return OperationType.ADD;
        in listOf("-") -> return OperationType.SUBTRACT;
        in listOf("*") -> return OperationType.MULTIPLY;
        in listOf("/") -> return OperationType.DIVIDE;
        in listOf("sin") -> return OperationType.SIN;
        in listOf("cos") -> return OperationType.COS;
        in listOf("sqrt") -> return OperationType.SQRT;
    }
    return OperationType.ADD;
}
fun priority(str: String): Int {
    when (str) {
        in listOf("+", "-") -> return 1
        in listOf("*", "/") -> return 2
        in listOf("sin", "cos", "sqrt") -> return 3
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
            if (!stack.isEmpty() && (stack.last() == "sin" || stack.last() == "cos" || stack.last() == "sqrt")) {
                returning.add(stack.removeLast());
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
fun fromOpz(arr: ArrayList<String>): Double? {
    if (arr.isEmpty()) {
        return null;
    }
    var stack = ArrayDeque<Double>();
    for (now in arr) {
        val part = now.toDoubleOrNull();
        if (part != null) {
            stack.addLast(part);
        } else if (priority(now) > 0) {
            val realOne = anotherPreobrazovatel(now);
            if (realOne == OperationType.SIN || realOne == OperationType.COS) {
                val a = stack.removeLast();
                val calculateResult = calculate(a, null, realOne);
                if (calculateResult != null) {
                    stack.addLast(calculateResult);
                } else {
                    return null;
                }
            } else {
                val b: Double? = stack.removeLast();
                val a: Double? = stack.removeLast();
                val calculateResult = calculate(a, b, anotherPreobrazovatel(now));
                if (calculateResult != null) {
                    stack.addLast(calculateResult)
                } else {
                    return null;
                }
            }
        }
    }
    return stack.last();
}
fun calculate(a: Double?, b: Double? = null, operation: OperationType = OperationType.ADD): Double? {
    if (a == null) {
        return null;
    }
    when (operation) {
        OperationType.ADD -> {
            if (b != null) {
                return a + b
            }
        }
        OperationType.SUBTRACT -> {
            if (b != null) {
                return a - b;
            }
        }

        OperationType.DIVIDE -> {
            if (b == 0.0 || b == null) {
                return null
            } else {
                return a/b
            }
        }
        OperationType.MULTIPLY -> {
            if (b != null) {
                return a * b;
            }
        }
        OperationType.COS -> {
            if (b == null) {
                return cos(Math.toRadians(a));
            }
        }
        OperationType.SIN -> {
            if (b == null) {
                return sin(Math.toRadians(a));
            }
        }
        OperationType.SQRT -> {
            if (b == null) {
                return sqrt(a);
            }
        }
    }
    return null;
}
fun main() {
/*    val arr = toOPZ("(sin (30) * 2 + 4) / 5 + 1")
    val res = fromOpz(arr)
    if (res == null){
        println("Кажется, в выражении что-то не так...")
    }
    for (k in arr){
        print(k)
        print(" ")
    }
    print("\n")
    print(res)*/
}
/**
 * Функция вычисления выражения, представленного строкой
 * @return результат вычисления строки или null, если вычисление невозможно
 * @sample "5 * 2".calculate()*/


@Suppress("ReturnCount")
fun String.calculate(): Double? {
    val arr = toOPZ(this);
    val res = fromOpz(arr)
    if (res == null){
        println("Кажется, в выражении что-то не так...")
    }
    return res;
}
