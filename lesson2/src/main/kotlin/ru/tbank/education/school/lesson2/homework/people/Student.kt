package ru.tbank.education.school.lesson2.homework.people

import ru.tbank.education.school.lesson2.homework.study.Exam
import ru.tbank.education.school.lesson2.homework.study.ExamResult
import java.time.LocalDate

class Student(
    id: String,
    name: String,
    birthDate: LocalDate,
    email: String,
    var faculty: String,
    internal val examStatuses:  MutableMap<Exam, Pair<ExamResult, Int>> = mutableMapOf()
) : Person(id, name, birthDate, email, Roles.STUDENT) {
    fun takePart(exam: Exam){
        examStatuses[exam] = Pair(ExamResult.Participating, -1);
        exam.join(this)
    }
    fun finishExam(exam: Exam){
        examStatuses[exam] = Pair(ExamResult.Finished, -1)
    }
    fun getGrade(exam: Exam): Int = exam.getGrade(this)
    override fun getRole(): Roles = Roles.STUDENT
    override fun getInfo(): Map<String, String> {
        val mainInfo = super.getInfo();
        return mainInfo + mapOf("faculty" to faculty);
    }
}
