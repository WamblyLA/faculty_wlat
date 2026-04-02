package com.example.library.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "readers")
data class Reader(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Email
    @NotBlank
    val email: String = "",

    val name: String = "",

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name= "readers_books",
        joinColumns = [JoinColumn(name = "reader_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: List<Book> = emptyList()
)
