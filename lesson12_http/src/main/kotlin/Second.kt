import java.net.HttpURLConnection
import java.net.URL

// ===========================================
// Задача 2. REST — полный CRUD
// ===========================================
// Цель: реализовать все CRUD-операции для ресурса /posts.
// API: https://jsonplaceholder.typicode.com/posts
//
// TODO 1: Реализовать sendRequest() — универсальную функцию отправки запросов
// TODO 2: Реализовать 5 CRUD-функций (ниже)
// TODO 3: Вызвать каждую функцию в main() и вывести результат
//
// Вопросы после выполнения:
//   - В чём разница между PUT и PATCH?
//   - Почему POST возвращает 201, а PUT возвращает 200?
//   - Какой метод идемпотентный, а какой нет?

const val BASE_URL = "https://jsonplaceholder.typicode.com/posts"

/** Универсальная функция для отправки HTTP-запросов.
 *  @param urlStr  — полный URL
 *  @param method  — HTTP-метод (GET, POST, PUT, DELETE)
 *  @param body    — тело запроса в формате JSON (null для GET/DELETE)
 *  @return Pair(statusCode, responseBody)
 */
fun sendRequest(urlStr: String, method: String, body: String? = null): Pair<Int, String> {
    val connection = URL(urlStr).openConnection() as HttpURLConnection
    return try {
        connection.requestMethod = method
        connection.connectTimeout = 10000
        connection.readTimeout = 10000
        if (body != null) {
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")
            connection.outputStream.write(body.toByteArray())
        }
        val code = connection.responseCode
        val stream = if (code in 200..299) connection.inputStream else connection.errorStream
        val response = stream?.bufferedReader()?.use { it.readText() } ?: ""
        code to response
    } catch (e: Exception) {
        -1 to "Ошибка: ${e.message}"
    } finally {
        connection.disconnect()
    }
}

/** GET /posts — получить все посты */
fun getPosts(): String {
    return sendRequest(BASE_URL, "GET").second
}

/** GET /posts/{id} — получить пост по ID */
fun getPost(id: Int): String {
    return sendRequest("$BASE_URL/$id", "GET").second
}

/** POST /posts — создать новый пост. Тело: {"title":"...", "body":"...", "userId":1} */
fun createPost(json: String): String {
    return sendRequest(BASE_URL, "POST", json).second
}

/** PUT /posts/{id} — полностью обновить пост */
fun updatePost(id: Int, json: String): String {
    return sendRequest("$BASE_URL/$id", "PUT", json).second
}

/** DELETE /posts/{id} — удалить пост, вернуть статус-код */
fun deletePost(id: Int): Int {
    return sendRequest("$BASE_URL/$id", "DELETE").first
}

fun main() {
    disableSslVerification()

    // TODO 3: вызвать каждую функцию и вывести результат
    println("=== GET ALL ===")
    println(getPosts())
    println("\n=== GET ONE ===")
    println(getPost(1))
    println("\n=== CREATE ===")
    val json = """
        {
            "title": "tle",
            "body": "smNew",
            "userId": 1
        }
    """.trimIndent()
    println(createPost(json))
    println("\n=== UPDATE ===")
    val jsonUp = """
        {
            "id": 1,
            "title": "tle",
            "body": "smNew",
            "userId": 1
        }
    """.trimIndent()
    println(updatePost(1, jsonUp))
    println("\n=== DELETE ===")
    println(deletePost(1))
}