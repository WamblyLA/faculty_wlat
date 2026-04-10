package com.example.library.repository

import com.example.library.entity.Reader
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

// ============================================================================
// ЗАДАНИЕ ФИНАЛ: Создать интерфейс ReaderRepository
// ============================================================================
// ИНСТРУКЦИЯ:
// 2. Создай интерфейс

interface ReaderRepository : JpaRepository<Reader, Long> {

    @Query(
        "SELECT DISTINCT r FROM Reader r " +
            "LEFT JOIN FETCH r.books b " +
            "LEFT JOIN FETCH b.author " +
            "LEFT JOIN FETCH b.genre"
    )
    fun findAllWithBooksFetched(): List<Reader>
}
