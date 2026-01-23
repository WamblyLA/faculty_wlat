import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun dateTimeLectureExample() {



    println(ZoneId.getAvailableZoneIds())


    // 1. Текущий момент времени (UTC)
    val instant = Instant.now()
    println("Instant: $instant")

    println()

    // 2. Локальная дата и время (без таймзоны)
    val localDate = LocalDate.now()
    val localTime = LocalTime.now()
    val localDateTime = LocalDateTime.now()

    println("LocalDate: $localDate")
    println("LocalTime: $localTime")
    println("LocalDateTime: $localDateTime")

    println()

    // 3. Дата и время с таймзоной
    val zonedDateTime = ZonedDateTime.now()
    val moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
    val laTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"))

    println("Zoned now: $zonedDateTime")
    println("Moscow: $moscowTime")
    println("Los Angeles: $laTime")

    println()

    // 4. OffsetDateTime (смещение от UTC, без региона)
    val offsetDateTime = OffsetDateTime.now()
    println("OffsetDateTime: $offsetDateTime")

    println()

    // 5. Создание конкретной даты и времени
    val birthday = LocalDate.of(1995, 7, 23)
    val meeting = LocalDateTime.of(2026, 1, 22, 9, 50)

    println("Birthday: $birthday")
    println("Meeting: $meeting")

    println()

    // 6. Парсинг строк
    val parsedDate = OffsetDateTime.parse("2023-07-23+03:00")
    val parsedDateTime = LocalDateTime.parse("2024-10-02T14:30")


    ZoneId.getAvailableZoneIds()

    println("Parsed date: $parsedDate")
    println("Parsed dateTime: $parsedDateTime")

    println()

    // 7. Форматирование
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    println("Formatted meeting: ${meeting.format(formatter)}")

    println()

    // 8. Операции с датами (immutable!)
    val tomorrow = localDate.plusDays(1)
    val nextMonth = localDate.plusMonths(1)
    val twoHoursLater = localDateTime.plusHours(2)

    println("Tomorrow: $tomorrow")
    println("Next month: $nextMonth")
    println("Two hours later: $twoHoursLater")

    println()

    // 9. Усечение (truncation)
    val truncatedToDay = localDateTime.truncatedTo(ChronoUnit.DAYS)
    println("Truncated to day: $truncatedToDay")

    println()

    // 10. Сравнение дат
    println("Is meeting after now: ${meeting.isAfter(localDateTime)}")
    println("Is birthday before now: ${birthday.isBefore(localDate)}")

    println()

    // 11. Разница между датами
    val daysBetween = ChronoUnit.DAYS.between(birthday, localDate)
    val hoursBetween = ChronoUnit.HOURS.between(localDateTime, meeting)

    println("Days since birthday: $daysBetween")
    println("Hours until meeting: $hoursBetween")

    println()

    // 12. Преобразования между типами
    val zonedFromLocal = localDateTime.atZone(ZoneId.systemDefault())
    val instantFromZoned = zonedFromLocal.toInstant()

    println("Zoned from local: $zonedFromLocal")
    println("Instant from zoned: $instantFromZoned")




    println()



}

fun main() {
    dateTimeLectureExample()
}