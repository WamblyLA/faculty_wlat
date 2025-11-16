package ru.tbank.education.school.lesson2.homework.study

import ru.tbank.education.school.lesson2.homework.people.Student
import java.time.LocalDate

class Exam (
    val id: String,
    conductDate: LocalDate,
    var description: String,
    var grades: MutableMap<Student, Int>,
    private var questions: MutableList<Question>,
) {
    fun getGrade(student: Student): Int {
        return grades[student] ?: -1
    }
    fun addQuestion(question: Question) {
        questions.add(question)
    }
    internal fun join(student: Student) {
        grades[student] = grades.getOrDefault(student, -1)
    }
    fun getParticipatingOnes(): List<Student> {
        return grades.keys.filter { student -> student.examStatuses[this]?.first == ExamResult.Participating }
    }
    internal var date: LocalDate = conductDate
        set(newDate) {
            if (newDate > LocalDate.now()) {
                field = newDate
            } else {
                throw IllegalArgumentException("Дата должна быть в будущем")
            }
        }
    fun setGrade(student: Student, grade: Int) {
        grades[student] = grade
    }
    fun getQuestions(): List<Question> {
        return questions
    }
}
