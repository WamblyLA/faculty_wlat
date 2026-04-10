package com.example.library.service

import com.example.library.entity.Author
import com.example.library.entity.Genre
import com.example.library.entity.Book
import com.example.library.repository.AuthorRepository
import com.example.library.repository.BookRepository
import com.example.library.repository.GenreRepository
import com.example.library.repository.ReaderRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional
import kotlin.jvm.java
import io.mockk.slot
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import kotlin.math.max
/**
 * ШАБЛОН ЗАНЯТИЯ (без готового кода тестов).
 *
 * Как работать:
 * 1. Сними [@Disabled] с класса ниже, когда начнёшь писать код.
 * 2. Для каждого теста следуй блоку «ИНСТРУКЦИЯ» — шаг за шагом.
 *
 * Подключи в начале файла импорты по мере необходимости, например:
 * - io.mockk: mockk, every, verify, slot, capture, any, eq, match
 * - org.junit.jupiter.api.Assertions.* (assertEquals, assertThrows — для проверки **результата** сервиса)
 * - Матчеры **eq** / **any** / **match** используются внутри `every { }` и `verify { }` для **аргументов** мока
 * - сущности и репозитории из com.example.library.*
 * - java.util.Optional — для findById у Spring Data
 * - org.springframework.data.domain.* — для постраничности
 */
@MockK
class LibraryServiceMockkTest {

    // ИНСТРУКЦИЯ (общая):
    // Объяви четыре репозитория как mockk<...>() — как в рабочем файле тестов.
    // Объяви lateinit var service: LibraryService
    // В @BeforeEach создай LibraryService, передав в конструктор все четыре мока.
    lateinit var service: LibraryService
    val authorRepository = mockk<AuthorRepository>()
    val bookRepository = mockk<BookRepository>()
    val genreRepository = mockk<GenreRepository>()
    val readerRepository = mockk<ReaderRepository>()
    @BeforeEach
    fun setUp() {
        /*
         * ИНСТРУКЦИЯ:
         * 1. Присвой service = LibraryService(authorRepository, bookRepository, genreRepository, readerRepository)
         */
        service = LibraryService(authorRepository, bookRepository, genreRepository, readerRepository)
    }

    @Test
    fun `createAuthor возвращает того же автора что вернул save`() {
        /*
         * ИНСТРУКЦИЯ — основы every и verify:
         * 1. Создай автора Author(name = "...") — как будто его только что создали в коде сервиса.
         * 2. Создай «сохранённого» автора с id (например 42L) и тем же именем.
         * 3. Напиши: every { authorRepository.save(eq(тот_что_без_id)) } returns сохранённый_с_id
         * 4. Вызови service.createAuthor("то же имя")
         * 5. assertEquals по id и имени результата (JUnit — проверка возвращаемого значения)
         * 6. verify(exactly = 1) { authorRepository.save(eq(тот_что_без_id)) } — матча eq в MockK
         */
        val author = Author(name = "John", books = listOf())
        val savedAuthor = Author(id=42L, name = "John", books = listOf())
        every {authorRepository.save(eq(author))} returns savedAuthor
        val res = service.createAuthor("John")
        assertEquals(savedAuthor.id to savedAuthor.name, res.id to res.name)
        verify(exactly = 1) {
            authorRepository.save(eq(author))
        }
    }

    @Test
    fun `getAllGenres возвращает список из genreRepository findAll`() {
        /*
         * ИНСТРУКЦИЯ — стаб (заглушка) возвращает данные:
         * 1. Собери список из двух Genre(id, name).
         * 2. every { genreRepository.findAll() } returns этот_список
         * 3. Вызови service.getAllGenres()
         * 4. Проверь размер списка и имя первого жанра (assertEquals).
         * 5. verify { genreRepository.findAll() } — убедись, что метод вызывался.
         */
        val genres: List<Genre> = listOf(Genre(id = 42L, name="Fiction"), Genre(id=52L, name="Some"))
        every {genreRepository.findAll() } returns genres
        val res = service.getAllGenres()
        assertEquals(genres[0].name to genres.size, res[0].name to res.size)
        verify { genreRepository.findAll() }
    }

    @Test
    fun `createBook бросает EntityNotFoundException если автор не найден`() {
        /*
         * ИНСТРУКЦИЯ — исключения и verify(exactly = 0):
         * 1. Настрой every { authorRepository.findById(eq(99L)) } returns Optional.empty()
         *    (в Kotlin: import java.util.Optional).
         * 2. Используй assertThrows(EntityNotFoundException::class.java) { service.createBook(...) }
         * 3. Проверь message у исключения (должен совпадать с текстом в LibraryService).
         * 4. verify(exactly = 1) { authorRepository.findById(eq(99L)) }
         * 5. verify(exactly = 0) { bookRepository.save(any()) } — книга не сохранялась.
         *    Для any() нужен импорт io.mockk.any (или звёздочка io.mockk.*).
         */
        every { authorRepository.findById(eq(99L)) } returns Optional.empty()
        val ex = assertThrows(EntityNotFoundException::class.java) {
            service.createBook(title="Fiction", authorId=99L, isbn = "1234567890", genreId = 42L);
        }
        assertEquals("Author not found with id: 99", ex.message);
        verify(exactly = 1) {
            authorRepository.findById(eq(99L));
        }
        verify(exactly = 0) {
            bookRepository.save(any());
        }
    }

    @Test
    fun `createBook передаёт в save книгу с нужным названием и ISBN slot ловит аргумент`() {
        /*
         * ИНСТРУКЦИЯ — slot и capture (поймать аргумент save):
         * 1. Создай author с id и genre с id.
         * 2. every { authorRepository.findById(eq(...)) } returns Optional.of(author)
         *    every { genreRepository.findById(eq(...)) } returns Optional.of(genre)
         * 3. Объяви val bookSlot = slot<Book>()
         * 4. every { bookRepository.save(capture(bookSlot)) } answers { bookSlot.captured.copy(id = 100L) }
         *    (или свой id — главное, чтобы answers вернул Book с id после save)
         * 5. Вызови service.createBook("название", "isbn", authorId, genreId)
         * 6. Проверь bookSlot.captured.title, isbn, author, genre
         * 7. Дополнительно: verify(exactly = 1) { bookRepository.save(match { it.title == "..." && it.isbn == "..." }) }
         */
        val author = Author(id = 42L, name = "John", books = listOf())
        val genre = Genre(id=52L, name="Fiction")
        every { authorRepository.findById(eq(42L)) } returns Optional.of(author)
        every { genreRepository.findById(eq(52L)) } returns Optional.of(genre)
        val bookSlot = slot<Book>()
        every { bookRepository.save(capture(bookSlot))  } answers { bookSlot.captured.copy(id = 67L)}
        val created = service.createBook("Название крутое", "isbn", 42L, 52L)
        assertEquals(created.copy(id=null), bookSlot.captured)
        verify (exactly = 1) {
            bookRepository.save(match { it.title == "Название крутое" && it.isbn == "isbn"})
        }
    }

    @Test
    fun `getBooksPage делегирует в bookRepository findAll с постраничностью`() {
        /*
         * ИНСТРУКЦИЯ — мок Page и точное совпадение аргумента:
         * 1. Создай минимум одну Book в списке (нужны author и genre для конструктора Book).
         * 2. Собери PageImpl(список, PageRequest.of(0, 20, Sort.by("title")), totalElements)
         *    (импорты из org.springframework.data.domain).
         * 3. Сохрани val pageRequest = PageRequest.of(0, 20, Sort.by("title")); every { bookRepository.findAll(eq(pageRequest)) } returns page
         * 4. Вызови service.getBooksPage(page = 0, size = 20)
         * 5. Проверь content.size и title первой книги (JUnit).
         * 6. verify(exactly = 1) { bookRepository.findAll(eq(pageRequest)) }
         */
        val author = Author(id = 42L, name = "John", books = listOf())
        val genre = Genre(id=52L, name="Fiction")
        val book = Book(id=67L, title="Any", isbn="12345", author = author, genre = genre)
        val books = mutableListOf<Book>()
        books.add(book)
        val booksPage = PageImpl(books, PageRequest.of(0, 20, Sort.by("title")), books.size.toLong())
        val pageRequest = PageRequest.of(0, 20, Sort.by("title"));
        every { bookRepository.findAll(eq(pageRequest))} returns booksPage;
        val abooks = service.getBooksPage(page = 0, size = 20);
        assertEquals(books[0] to max(books.size, 20), abooks.content[0] to abooks.size)
        verify (exactly = 1) {
            bookRepository.findAll(eq(pageRequest))
        }
    }
}
