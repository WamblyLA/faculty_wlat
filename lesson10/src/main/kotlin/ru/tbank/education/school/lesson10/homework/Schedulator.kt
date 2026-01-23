package ru.tbank.education.school.lesson10.homework
import java.time.LocalDateTime
import java.time.Duration
data class Normalized (
    val dt: LocalDateTime? = null,
    val id: Int? = null,
    val status: String? = null
)
data class Event (
    val id: Int? = null,
    val sentDt: LocalDateTime? = null,
    val deliveredDt: LocalDateTime? = null,
    val duration: Long? = null,
    val isInvalid: Boolean = false,
    val isTimeError: Boolean = false
)
val regexA = Regex("""^\s*(\d{4})\s*-\s*(\d{2})\s*-\s*(\d{2})\s+(\d{2})\s*:\s*(\d{2})\s*\|\s*ID\s*:\s*(\d+)\s*\|\s*STATUS\s*:\s*(sent|delivered)\s*$""", RegexOption.IGNORE_CASE)
val regexB = Regex("""^\s*TS\s*=\s*(\d{2})\s*/\s*(\d{2})\s*/\s*(\d{4})\s*-\s*(\d{2})\s*:\s*(\d{2})\s*;\s*status\s*=\s*(sent|delivered)\s*;\s*#\s*(\d+)\s*$""", RegexOption.IGNORE_CASE)
val regexC = Regex("""^\s*\[\s*(\d{2})\s*\.\s*(\d{2})\s*\.\s*(\d{4})\s+(\d{2})\s*:\s*(\d{2})\s*\]\s*(sent|delivered)\s*\(\s*id\s*:\s*(\d+)\s*\)\s*$""", RegexOption.IGNORE_CASE)
fun normalize(stroka: String): Normalized {
    var newStroka = stroka.trim();
    regexA.matchEntire(newStroka)?.let {match ->
        val all = match.groupValues
        val id = all[6].toInt()
        val status = all[7].lowercase()
        val dt = LocalDateTime.of(
            all[1].toInt(),
            all[2].toInt(),
            all[3].toInt(),
            all[4].toInt(),
            all[5].toInt(),
        )
        return Normalized(dt, id, status)
    }
    regexB.matchEntire(newStroka)?.let {match ->
        val all = match.groupValues
        val id = all[7].toInt()
        val status = all[6].lowercase()
        val dt = LocalDateTime.of(
            all[3].toInt(),
            all[2].toInt(),
            all[1].toInt(),
            all[4].toInt(),
            all[5].toInt(),
        )
        return Normalized(dt, id, status)
    }
    regexC.matchEntire(newStroka)?.let {match ->
        val all = match.groupValues
        val id = all[7].toInt()
        val status = all[6].lowercase();
        val dt = LocalDateTime.of(
            all[3].toInt(),
            all[2].toInt(),
            all[1].toInt(),
            all[4].toInt(),
            all[5].toInt(),
        )
        return Normalized(dt, id, status)
    }
    return Normalized()
}
fun applyToAll(logs: List<String>): MutableList<Normalized> {
    val ans = mutableListOf<Normalized>()
    for (log in logs) {
        val newStroka = normalize(log)
        ans.add(newStroka)
    }
    return ans;
}
fun eventCreation(logs: MutableList<Normalized>): List<Event> {
    val grouped = logs.filter {it.id != null}.groupBy {it.id!! };
    return grouped.map {(id, events) ->
        val sentDt = events.filter {it.status == "sent" }.mapNotNull { it.dt }.minOrNull()
        val deliveredDt = events.filter {it.status == "delivered"}.mapNotNull { it.dt }.minOrNull()
        val isInvalid = (deliveredDt == null || sentDt == null)
        var isTimeError = false
        if (!isInvalid) {
            isTimeError = deliveredDt!!.isBefore(sentDt!!)
        }
        var dur: Long? = null;
        if (!isInvalid && !isTimeError) {
            dur = Duration.between(sentDt, deliveredDt).toMinutes()
        }
        Event(
            id = id,
            sentDt = sentDt,
            deliveredDt = deliveredDt,
            duration = dur,
            isInvalid = isInvalid,
            isTimeError = isTimeError
        )
    }
}
fun otchet(events: List<Event>, forBC: MutableList<Normalized>) {
    val sorted = events.filter {!it.isInvalid && !it.isTimeError}.sortedByDescending{ it.duration!! }
    sorted.forEach { event ->
        println("${event.id} ${event.duration}")
    }
    print('\n')
    println("Самая долгая: ${sorted.firstOrNull()?.id} ${sorted.firstOrNull()?.duration}")
    print('\n')
    val transpassers = sorted.filter { it.duration!! > 20}.map{it.id}
    println(transpassers)
//    println(events.filter {it.isInvalid}.map{it.id})
//    println(events.filter {it.isTimeError}.map{it.id})
    //Не понял, обязательно ли это, но оставил
    println("\nДополнительные пункты: ")
    println("Пункт B: самый загруженный час")
    println(forBC.filter{it.status == "delivered"}.groupingBy { it.dt!!.hour}.eachCount().maxBy { it.value }.key)
}
fun main() {
    val logs = listOf(
        "2026-01-22 09:14 | ID:042 | STATUS:sent",
        "TS=22/01/2026-09:27; status=delivered; #042",
        "2026-01-22 09:10 | ID:043 | STATUS:sent",
        "2026-01-22 09:18 | ID:043 | STATUS:delivered",
        "TS=22/01/2026-09:05; status=sent; #044",
        "[22.01.2026 09:40] delivered (id:044)",
        "2026-01-22 09:20 | ID:045 | STATUS:sent",
        "[22.01.2026 09:33] delivered (id:045)",
        "   ts=22/01/2026-09:50; STATUS=Sent; #046   ",
        " [22.01.2026 10:05]   DELIVERED   (ID:046) "
    )
//    val res = eventCreation(applyToAll(logs))
    val applied = applyToAll(logs)
    val res = eventCreation(applied)
    println(otchet(res, applied))
}
