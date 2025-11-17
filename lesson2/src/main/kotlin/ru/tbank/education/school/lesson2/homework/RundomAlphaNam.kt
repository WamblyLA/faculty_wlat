package ru.tbank.education.school.lesson2.homework
fun generateRandomString(length: Int): String { //Как по мне так в разы проще генерировать id
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return String(CharArray(length) { allowedChars.random() });
}