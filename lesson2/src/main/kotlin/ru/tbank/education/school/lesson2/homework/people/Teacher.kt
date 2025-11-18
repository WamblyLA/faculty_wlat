package ru.tbank.education.school.lesson2.homework.people

import ru.tbank.education.school.lesson2.homework.University
import ru.tbank.education.school.lesson2.homework.study.ExamResult
import ru.tbank.education.school.lesson2.homework.study.Exam
import ru.tbank.education.school.lesson2.homework.study.Question
import java.time.LocalDate
import ru.tbank.education.school.lesson2.homework.generateRandomString
class Teacher (
    id: String,
    name: String,
    birthDate: LocalDate,
    email: String,
    var cadefr: String,
    var position: String,
) : Person(id, name, birthDate, email, Roles.TEACHER) {
    override fun getRole(): Roles = Roles.TEACHER
    override fun getInfo(): Map<String, String> {
        val mainInfo = super.getInfo();
        return mainInfo + mapOf("cafedr" to cadefr, "position" to position);
    }
    fun createExam(university: University, name: String, conductDate: LocalDate, desc: String): Exam {
        val exam = Exam(id = generateRandomString(10), name = name, teacher = this, conductDate = conductDate, description = desc)
        university.addExam(exam)
        return exam
    }
    fun deleteExam(university: University, exam: Exam) {
        if (exam.teacher == this) {
            university.removeExam(exam)
        } else {
            throw Exception("Нельзя удалить не принадлежащий экзамен")
        }
    }

    fun addQuestions(exam: Exam, mapa: List<Map<String,String>>) { //Я подумал так логичнее, структуру Question они не знают, а вот мапа в разы понятнее
        for (map in mapa){
            val q = Question(
                id = generateRandomString(10),
                text = map.getOrDefault("text", ""),
                answer = map.getOrDefault("answer", ""),
            )
            exam.addQuestion(q);
        }
    }
    fun rateStudent(exam: Exam, student: Student, grade: Int) {
        if (student.examStatuses[exam]?.first == ExamResult.Finished) {
            student.examStatuses[exam] = Pair(ExamResult.Rated, grade);
            exam.setGrade(student, grade)
        } else if (student.examStatuses[exam]?.first  == ExamResult.Participating) {
            throw Exception ("${student.name} еще не завершил экзамен")
        } else {
            throw Exception("${student.name} не участвовал или уже оценен")
        }
    }
    fun getStudentsForGrades(exam: Exam): List<Student> {
        return exam.grades.keys.filter { student -> student.examStatuses[exam]?.first == ExamResult.Participating }
    }
}