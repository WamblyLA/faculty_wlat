package com.example.library.repository

import com.example.library.entity.Genre
import org.springframework.data.jpa.repository.JpaRepository
// ============================================================================
// ЗАДАНИЕ 1: Создать интерфейс GenreRepository
// ============================================================================
// ИНСТРУКЦИЯ: Убери NoRepositoryBean ниже чтобы этот класс появился как репозиторий, а так же избавься от дефолтной реализации

interface GenreRepository  : JpaRepository<Genre, Long>