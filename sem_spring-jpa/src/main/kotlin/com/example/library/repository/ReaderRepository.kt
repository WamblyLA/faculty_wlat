package com.example.library.repository

import com.example.library.entity.Reader
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

// ============================================================================
// ЗАДАНИЕ ФИНАЛ: Создать интерфейс ReaderRepository
// ============================================================================
// ИНСТРУКЦИЯ:
// 1. Создай интерфейс

interface ReaderRepository : JpaRepository<Reader, Long> {
    @Query("SELECT DISTINCT r FROM Reader r JOIN FETCH r.books")
    fun findAllWithBooks(): List<Reader>
}