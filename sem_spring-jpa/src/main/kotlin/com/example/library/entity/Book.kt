package com.example.library.entity

import jakarta.persistence.*
import com.example.library.entity.Reader
@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val title: String,
    val isbn: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    val author: Author? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    val genre: Genre? = null,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    val readers: List<Reader> = emptyList()
)
