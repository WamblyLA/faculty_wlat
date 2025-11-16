package ru.tbank.education.school.lesson2.homework
fun generateRandomString(length: Int): String { //Взял это из интернета, как по мне так в разы проще генерировать id
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return String(CharArray(length) { allowedChars.random() });
}