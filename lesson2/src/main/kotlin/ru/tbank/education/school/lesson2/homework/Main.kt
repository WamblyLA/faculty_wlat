package ru.tbank.education.school.lesson2.homework

import ru.tbank.education.school.lesson2.homework.people.Student
import ru.tbank.education.school.lesson2.homework.people.Teacher
import java.time.LocalDate

fun main() {
    val university = University()
    val teacher = Teacher(
        id = generateRandomString(10),
        name = "Ivan Ivanov",
        birthDate = LocalDate.of(1990, 1, 1),
        cadefr = "FCS",
        email = "tbank@tbank.com",
        position = "Head teacher"
    )
    val student = Student(
        id = generateRandomString(10),
        name = "Petr Petrov",
        birthDate = LocalDate.of(2005, 1, 1),
        email = "mail@email.com",
        faculty = "Economics and Data Analysis"
    )
    university.addPerson(student)
    university.addPerson(teacher)
    val exam = teacher.createExam(university, "Module 1 Exam", LocalDate.of(2025,11,20), desc="This is our first exam")
    teacher.addQuestions(exam, listOf(mapOf("first" to "1+1", "second" to "2+2"), mapOf("first" to "2+3"), mapOf("second" to "3+4")))
    println("Вопросов теперь ${exam.getAmountOfQuestions()}")
    student.takePart(exam);
    student.finishExam(exam);
    teacher.rateStudent(exam, student, 15);
    println("Студент оценен в ${student.getGrade(exam)}")
    teacher.deleteExam(university,exam);
}
