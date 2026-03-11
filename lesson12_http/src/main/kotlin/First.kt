import java.net.HttpURLConnection
import java.net.URL
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
// ===========================================
// Задача 1. HTTP-запросы через HttpURLConnection
// ===========================================
// Цель: научиться отправлять GET и POST запросы, читать ответ и статус-код.
// API: https://jsonplaceholder.typicode.com
//
// TODO 1: Отправить GET /posts/1, вывести статус-код и тело ответа
// TODO 2: Отправить POST /posts с JSON-телом, вывести статус-код и тело
// TODO 3: Отправить GET /posts/9999, обработать ошибку (код != 2xx)
//
// Подсказки:
//   val connection = URL("...").openConnection() as HttpURLConnection
//   connection.requestMethod = "GET"             — задать метод
//   connection.doOutput = true                   — разрешить отправку тела
//   connection.setRequestProperty("Content-Type", "application/json") — заголовок
//   connection.outputStream.write(json.toByteArray())                 — записать тело
//   connection.responseCode                      — получить статус-код
//   connection.inputStream.bufferedReader().readText()  — прочитать тело ответа
//   connection.errorStream                       — поток ошибок (при коде 4xx/5xx)
//   connection.disconnect()                      — закрыть соединение


fun disableSslVerification() {
    val trustAll = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAll, java.security.SecureRandom())
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
    HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
}
fun ask(method: String, urlka: String, body: String? = null) {
    val getUrl = URL("https://jsonplaceholder.typicode.com" + urlka)
    println("URL: $getUrl")
    val conn = getUrl.openConnection() as HttpURLConnection
    try {
        conn.requestMethod = method
        if (body != null) {
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            conn.outputStream.write(body.toByteArray())
        }
        val code = conn.responseCode
        println("Код: ${code}")
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val response = stream?.bufferedReader()?.readText()
        println("Ответ: $response")
        if (code !in 200..299) {
            println("Запрос получил ошибку")
        }
    } catch (e: Exception) {
        println(e.message)
    } finally {
        conn.disconnect()
    }
}
fun main() {
    disableSslVerification()

    // TODO 1: GET /posts/1
    // === GET запрос ===
    println("=== GET /posts/1 ===")
    ask("GET", "/posts/1")
    // TODO 2: POST /posts
    println("\n=== POST /posts ===")
    val json = """
        {
          "title": "tle",
          "body": "somebody",
          "userId": 1
        }
    """.trimIndent()
    ask("POST", "/posts", json)
//    // TODO 3: GET /posts/9999 (несуществующий ресурс)
    println("\n=== GET /posts/9999 ===")
    ask("GET", "/posts/9999")
}