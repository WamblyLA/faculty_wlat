package ru.tbank.education.school.lesson2.homework

import ru.tbank.education.school.lesson2.homework.people.Person
import ru.tbank.education.school.lesson2.homework.people.Student
import ru.tbank.education.school.lesson2.homework.people.Teacher
import ru.tbank.education.school.lesson2.homework.study.Exam
class University {
    private val people = mutableListOf<Person>()
    private val exams = mutableListOf<Exam>()
    fun addPerson(person: Person) {
        people.add(person)
    }
    fun removePerson(person: Person) {
        people.remove(person)
    }
    fun addExam(exam: Exam) {
        exams.add(exam)
    }
    fun removeExam(exam: Exam) {
        exams.remove(exam)
    }
    fun getExams(): List<Exam> = exams
    fun getStudents(): List<Student> = people.filterIsInstance<Student>()
    fun getTeachres(): List<Teacher> = people.filterIsInstance<Teacher>()
}