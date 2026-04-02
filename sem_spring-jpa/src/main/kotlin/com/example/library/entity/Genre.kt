package com.example.library.entity

import jakarta.persistence.*

// ============================================================================
// ЗАДАНИЕ 1: Заполнить сущность Genre
// ============================================================================

@Entity
@Table(name = "genres")
data class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = ""
)
