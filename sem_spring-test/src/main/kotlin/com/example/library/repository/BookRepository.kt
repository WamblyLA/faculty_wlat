package com.example.library.repository

import com.example.library.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BookRepository : JpaRepository<Book, Long> {
    
    @EntityGraph(attributePaths = ["author", "genre"])
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    fun findByTitleContaining(@Param("title") title: String): List<Book>
    
    @EntityGraph(attributePaths = ["author", "genre"])
    override fun findAll(pageable: Pageable): Page<Book>
}
