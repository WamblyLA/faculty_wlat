package com.example.library.entity

import jakarta.persistence.*

// ============================================================================
// ЗАДАНИЕ ФИНАЛ: Создать сущность Reader
// ============================================================================
// ИНСТРУКЦИЯ:
// 1. Добавь @Entity и @Table
// 2. Добавь поля id, name, email

@Entity
@Table(name = "readers")
data class Reader(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = "",
    val email: String = "",

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "reader_books",
        joinColumns = [JoinColumn(name = "reader_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: MutableSet<Book> = HashSet()
)
