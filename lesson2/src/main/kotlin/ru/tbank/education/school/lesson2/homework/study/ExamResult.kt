package ru.tbank.education.school.lesson2.homework.study

sealed class ExamResult {
    object NotParticipated: ExamResult()
    object Participating: ExamResult()
    object Finished: ExamResult()
    object Rated: ExamResult()
}