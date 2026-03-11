import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64

// ===========================================
// Задача 3. JWT — авторизация
// ===========================================
// Цель: понять структуру JWT, собрать и декодировать токен, отправить запрос с Bearer-авторизацией.
// API: https://httpbin.org/bearer (возвращает 200 если есть Bearer, 401 если нет)
//
// TODO 1: Собрать JWT из трёх частей (header, payload, signature) в Base64URL
// TODO 2: Декодировать JWT обратно — вывести header и payload как JSON
// TODO 3: Отправить GET https://httpbin.org/bearer с заголовком Authorization: Bearer <token>
// TODO 4: Отправить тот же запрос БЕЗ токена — убедиться, что вернулся 401
// TODO 5: Подменить payload (role: student → admin), объяснить почему сервер отвергнет
//
// Подсказки:
//   Base64.getUrlEncoder().withoutPadding().encodeToString(bytes) — кодирование
//   Base64.getUrlDecoder().decode(string)                        — декодирование
//   JWT = base64(header) + "." + base64(payload) + "." + base64(signature)
//
// Вопросы после выполнения:
//   - Из каких 3 частей состоит JWT?
//   - Можно ли подменить payload и использовать токен? Почему нет?
//   - Что такое access token и refresh token?
fun reqGet(urlStr: String, token: String? = null): Pair<Int, String> {
    val connection = URL(urlStr).openConnection() as HttpURLConnection
    return try {
        connection.requestMethod = "GET"
        connection.connectTimeout = 10000
        connection.readTimeout = 10000
        if (token != null) {
            connection.setRequestProperty("Authorization", "Bearer $token")
        }
        val code = connection.responseCode
        val stream = if (code in 200..299) connection.inputStream else connection.errorStream
        val res = stream?.bufferedReader()?.use {it.readText()} ?: ""
        code to res
    } catch (e: Exception) {
        -1 to "Ошибка: ${e.message}"
    } finally {
        connection.disconnect()
    }
}
fun main() {
    disableSslVerification()

    val encoder = Base64.getUrlEncoder().withoutPadding()
    val decoder = Base64.getUrlDecoder()

    println("=== Сборка JWT ===")
    var header = """{"alg":"HS256","typ":"JWT"}"""
    var payload = """{"sub":"1","name":"Ivan Petrov","role":"student","iat":1234567890}"""
    var fakeSignature = "dummysignature"

    val headerEncoded = encoder.encodeToString(header.toByteArray())
    val payloadEncoded = encoder.encodeToString(payload.toByteArray())
    val signatureEncoded = encoder.encodeToString(fakeSignature.toByteArray())

    var token = headerEncoded + "." + payloadEncoded + "." + signatureEncoded;
    println(token)

    println("\n=== Декодирование JWT ===")
    val res = token.split('.').map { decoder.decode(it).toString(Charsets.UTF_8) }
    println(res)

    println("\n=== GET /bearer (с токеном) ===")
    val withToken = reqGet("https://httpbin.org/bearer", token)
    println("Код: ${withToken.first}")
    println("Тело: ${withToken.second}")

    println("\n=== GET /bearer (без токена) ===")
    val withoutToken = reqGet("https://httpbin.org/bearer")
    println("Код: ${withoutToken.first}")
    println("Тело: ${withoutToken.second}")

    println("\n=== Подмена payload ===")
    payload = """{"sub":"1","name":"Ivan Petrov","role":"admin","iat":1234567890}"""
    val encodedFakePld = encoder.encodeToString(payload.toByteArray())
    val fakeToken = "$headerEncoded.$encodedFakePld.$signatureEncoded"
    println("Поддельный токен: $fakeToken")
    val fakeResp = reqGet("https://httpbin.org/bearer", fakeToken)
    println("Код: ${fakeResp.first}")
    println("Тело: ${fakeResp.second}")
}