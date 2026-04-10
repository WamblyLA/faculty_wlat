package com.example.library.repository

import com.example.library.entity.Author
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthorRepository : JpaRepository<Author, Long> {
    
    // ✅ Метод для исправления N+1 (используй его в Задании 2!), надо исправить чтобы он получал автора с книгами через FETCH LEFT ....
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    fun findAllWithBooksFetched(): List<Author>
    
    // ❌ Этот метод вызывает N+1 проблему
    override fun findAll(): List<Author>
}
