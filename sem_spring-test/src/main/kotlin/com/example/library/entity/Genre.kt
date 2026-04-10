package com.example.library.entity

import jakarta.persistence.*

// ============================================================================
// ЗАДАНИЕ 1: Заполнить сущность Genre
// ============================================================================
// ИНСТРУКЦИЯ:
// 1. Добавь аннотации @Entity и @Table перед data class
// 2. Добавь @Id и @GeneratedValue перед id

@Entity
@Table(name = "genres")
data class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = ""
)
