package ru.tbank.education.school.lesson7

data class User(
    val name: String,
    val orders: List<Order>
)

data class Order(
    val id: Long,
    val product: String,
    val amount: Long,
    var isPaid: Boolean = false,
    var isDelivered: Boolean = false
)

// Задание 1 - выведите все заказы
fun task() {
    val users = listOf(
        User(
            "Анна", listOf(
                Order(1, "Телефон", 10000),
                Order(2, "Чехол", 100)
            )
        ),
        User(
            "Борис", listOf(
                Order(3, "Книга", 50),
                Order(4, "Рюкзак", 200)
            )
        )
    )
    users.map {println(it.orders)};
}


// Задание 2 - выведите отчет в формате месяц-прибыль
fun task2() {
    val months = listOf("Янв", "Фев", "Мар", "Апр", "Май")
    val revenue = listOf(1000, 1200, 800, 1400, 1300)
    val otchet = months.zip(revenue) {(m,r) -> "$m $r"}
    println(otchet)
}

// Задание 3 - выведите id всех заказов, которые были доставлены и оплачены на сумму > 1000
fun task3() {
    val orders = listOf(
        Order(id = 1, product = "Чехол", amount = 1200, isPaid = true, isDelivered = false),
        Order(id = 2, product = "Телефон", amount = 20000, isPaid = true, isDelivered = false),
        Order(id = 3, product = "Рюкзак", amount = 1000, isPaid = true, isDelivered = true),
        Order(id = 4, product = "Кружка", amount = 500, isPaid = false, isDelivered = false)
    )
    val filterOrders = orders.filter { it.isDelivered && it.amount > 1000 && it.isPaid }
    println(filterOrders)
}


data class Student(
    val name: String,
    val group: String
)


// Задание 4 - выведите кажду группу и студентов в ней
fun task4() {
    val students = listOf(
        Student(name = "Анна", group = "A-01"),
        Student(name = "Борис", group = "A-02"),
        Student(name = "Виктор", group = "A-01"),
        Student(name = "Галина", group = "A-03"),
        Student(name = "Денис", group = "A-02")
    )
    val gb = students.groupingBy { it.group }.eachCount()
    println(gb)
}

data class ApiResponse(val code: Int, val message: String)

// Задание 5 - получить первый успешный ответ и последний ответ с ошибкой на стороне сервера
fun task5() {
    val responses = listOf(
        ApiResponse(code = 500, message = "Internal Error"),
        ApiResponse(code = 404, message = "Not Found"),
        ApiResponse(code = 200, message = "OK"),
        ApiResponse(code = 200, message = "Cached OK")
    )
    val firstOk = responses.firstOrNull {it.code == 200}
    val lastServ = responses.lastOrNull {it.code == 500}
    println(firstOk)
    println(lastServ)
}

data class Movie(val title: String, val rating: Double)

// Задание 6 - получить топ 3 фильма по рейтингу
fun task6() {
    val movies = listOf(
        Movie(title = "Интерстеллар", rating = 9.0),
        Movie(title = "Начало", rating = 8.8),
        Movie(title = "Дюна", rating = 8.2),
        Movie(title = "Тёмный рыцарь", rating = 9.1),
        Movie(title = "Мементо", rating = 8.5)
    )
    val sortMovies = movies.sortedBy { it.rating }.take(n=3)
    println(sortMovies)
}

// Задание 7 - добавить логирование операций
fun task7() {
    val add: (a: Int, b: Int) -> Int = { a, b -> a + b }
    val subtract: (a: Int, b: Int) -> Int = { a, b -> a - b }
    val multiply: (a: Int, b: Int) -> Int = { a, b -> a * b }
}
fun withLogs(operation: (a: Int, b:Int) -> Int) =
{ (a,b) ->
    println("Parameters $a $b")
    val resukt = operation(a,b)
    println(resukt)
}
data class Client(
    val name: String,
    val email: String,
    val phone: String,
)


fun <A, B, C> ((A) -> B).andThen(next: (B) -> C): (A) -> C = { a -> next(this(a)) }
fun trim(field: String) = field.replace(" ", "")
fun lower(field: String) = field.lowercase()
fun validate(field: String) = if (field.isEmpty()) null else field
// Задание 8 - хочу написать функции для валидации полей
fun task8() {
    val rawClients = listOf(
        Client(name = "  Иван  ", email = "  IVAN@MAIL.RU  ", phone = " +7 (999) 123-45-67 "),
        Client(name = "  Мария  ", email = "maria@mail.ru", phone = "8-800-555-35-35"),
        Client(name = " ", email = "test@", phone = "000"),
    )
    val name = ""
    val validateFunc = ::trim.andThen { ::lower }.andThen { ::validate }

}

// Задание 9 - просто смотрим на примеры
fun task9() {
    val student = "Коля" to 14  // Создаем пару (Имя, Возраст)
    val subject = "Математика" to 5  // Создаем пару (Предмет, Оценка)

    println("Ученик: ${student.first}, возраст: ${student.second}")
    println("Предмет: ${subject.first}, оценка: ${subject.second}")



    print("Обратный отсчёт: ")
    for (i in 5 downTo 1) {
        print("$i..")
    }


    print("Чётные числа от 2 до 10: ")
    for (num in 2..10 step 2) {
        print("$num ")
    }

    val fruits = listOf("яблоко", "банан", "апельсин", "груша")
    print("Первые 3 фрукта: ")
    for (i in 0 until 3) {  // Только 0, 1, 2 (без последнего)
        print("${fruits[i]} ")
    }
}

// Задание 10 - напишите функцию деления с использованием runCatching и Result<T>, реализуйте вывод ошибки и реузльтата
fun task10() {
}

// Задание 11 - пример
fun task11() {
//    val sayHello = { println("Hello") }
//    sayHello()

    val sayHello: Function0<*> = object : Function0<Any?> {
        override fun invoke(): Any {
            println("Hello")
            return Unit
        }
    }
    sayHello.invoke()
}

val lambda = object : Function0<Unit> {
    override fun invoke() {
        println("Hello")
    }
}

val lambdaFun = { println("Hello") }

// 0 параметров
interface Function0<out R> {
    fun invoke(): R
}

// 1 параметр
interface Function1<in P1, out R> {
    fun invoke(p1: P1): R
}

// 2 параметра
interface Function2<in P1, in P2, out R> {
    fun invoke(p1: P1, p2: P2): R
}


fun main() {
    task()
    task2()
    task3()
    task4()
}
