package ru.tbank.education.school.lesson2.homework.people
import java.time.LocalDate
abstract open class Person(
    val id: String,
    val name: String,
    val birthDate: LocalDate,
    email: String,
    val personRole: Roles,
) {
    protected var email: String = email
        set(newOne) {
            if (newOne.contains("@") && newOne.contains(".")) {
                field = newOne.lowercase();
            } else {
                throw IllegalArgumentException("Кажется, это совсем не email...");
            }
        }
    val age: Int
        get() = LocalDate.now().year - birthDate.year
    open fun getRole(): Roles = personRole
    open fun getInfo(): Map<String, String> {
        return mapOf(
            "id" to id,
            "name" to name,
            "birthDate" to birthDate.toString(),
            "email" to email,
            "age" to age.toString()
        )
    }


}