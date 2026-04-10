package com.example.library.controller

import com.example.library.entity.Book
import com.example.library.entity.Genre
import com.example.library.service.LibraryService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/library")
class LibraryController(
    private val libraryService: LibraryService
) {
    
    // ==================== АВТОРЫ ====================
    
    @GetMapping("/authors")
    fun getAllAuthors(): ResponseEntity<List<Map<String, Any?>>> {
        return ResponseEntity.ok(libraryService.getAllAuthorsWithBooksCountNPlus1())
    }
    
    @GetMapping("/authors/{id}")
    fun getAuthor(@PathVariable id: Long): ResponseEntity<Map<String, Any?>> {
        return libraryService.getAuthorWithBooksFetched(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
    
    @PostMapping("/authors")
    fun createAuthor(@RequestBody @Valid request: CreateAuthorRequest): ResponseEntity<Map<String, Any?>> {
        val author = libraryService.createAuthor(request.name)
        return ResponseEntity.ok(mapOf("id" to author.id, "name" to author.name))
    }
    
    // ==================== КНИГИ ====================
    
    @GetMapping("/books")
    fun getBooks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<Map<String, Any?>> {
        val booksPage: Page<Book> = libraryService.getBooksPage(page, size)
        return ResponseEntity.ok(mapOf(
            "content" to booksPage.content.map { book ->
                mapOf(
                    "id" to book.id,
                    "title" to book.title,
                    "isbn" to book.isbn,
                    "author" to book.author?.name,
                    "genre" to book.genre?.name
                )
            },
            "totalPages" to booksPage.totalPages,
            "totalElements" to booksPage.totalElements,
            "currentPage" to booksPage.number,
            "size" to booksPage.size
        ))
    }
    
    @GetMapping("/books/search")
    fun searchBooks(@RequestParam title: String): ResponseEntity<List<Map<String, Any?>>> {
        return ResponseEntity.ok(libraryService.searchBooksByTitle(title))
    }
    
    @PostMapping("/books")
    fun createBook(@RequestBody @Valid request: CreateBookRequest): ResponseEntity<Map<String, Any?>> {
        val book = libraryService.createBook(request.title, request.isbn, request.authorId, request.genreId)
        return ResponseEntity.ok(mapOf(
            "id" to book.id,
            "title" to book.title,
            "isbn" to book.isbn
        ))
    }
    
    // ==================== ЖАНРЫ ====================
    
    // ========================================================================
    // ЗАДАНИЕ 1: Добавить endpoint для получения жанров
    // ========================================================================
    // ИНСТРУКЦИЯ: Раскомментируй метод ниже

    @GetMapping("/genres")
    fun getAllGenres(): ResponseEntity<List<Genre>> {
        return ResponseEntity.ok(libraryService.getAllGenres())
    }

    // ==================== ЧИТАТЕЛИ (финальное задание) ====================

    @GetMapping("/readers")
    fun getAllReaders(): ResponseEntity<List<Map<String, Any?>>> {
        return ResponseEntity.ok(libraryService.getAllReadersWithBooks())
    }
}

// ==================== DTO ====================

data class CreateAuthorRequest(
    @field:NotBlank(message = "Name is required")
    val name: String
)

data class CreateBookRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    
    @field:NotBlank(message = "ISBN is required")
    val isbn: String,
    
    @field:NotNull(message = "Author ID is required")
    val authorId: Long,
    
    @field:NotNull(message = "Genre ID is required")
    val genreId: Long
)
