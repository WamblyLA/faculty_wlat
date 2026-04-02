package com.example.library.entity

import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val name: String,
    
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    val books: List<Book> = emptyList()
)
