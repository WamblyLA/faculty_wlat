# 📚 Семинар: Spring JPA + Hibernate

Каждое задание нужно запустить и проверить.

Так как проект многомодульный, запуск делать через
```bash
./gradlew :sem_spring-jpa:bootRun
```
---

## 🚀 Быстрый старт

```bash
# Запустить приложение
./gradlew bootRun

# Проверить что работает
curl http://localhost:8080/api/library/authors
# Ответ: [{"id":1,"name":"Айзек Азимов","booksCount":2,...}]

# H2 Console
http://localhost:8080/h2-console
JDBC: jdbc:h2:mem:librarydb
User: sa
Password: (пустой)
```

---

## 📋 Задания

### ✅ Задание 0: Базовая версия (уже работает)

**Статус:** ✅ Готово

Эта версия уже компилируется и запускается.

**Что работает:**
- ✅ Таблицы создаются (V1, V2 миграции)
- ✅ Авторы и книги в базе
- ✅ API: `/api/library/authors`, `/api/library/books`

**Проверка:**
```bash
./gradlew bootRun
curl http://localhost:8080/api/library/authors
```

---

### 📝 Задание 1: Жанры

Необходимо создать новую сущность - жанр и связать её с книгами.

**Файлы для изменения:**
1. `src/main/resources/db/migration/V3__add_genres.sql` — раскомментировать SQL
2. `src/main/kotlin/.../entity/Genre.kt` — доработать согласно заданию
3. `src/main/kotlin/.../repository/GenreRepository.kt`
4. `src/main/kotlin/.../controller/LibraryController.kt`

**Проверка:**
```bash
./gradlew bootRun
curl http://localhost:8080/api/library/genres
# Ответ: [{"id":1,"name":"Фантастика"}, ...]
```

---

### 📝 Задание 2: Исправить N+1

Необходимо исправить метод получения авторов в `LibraryService` чтобы избежать N+1 проблемы.

**Файл для изменения:**
- `src/main/kotlin/.../service/LibraryService.kt` — заменить `findAll()` на `findAllWithBooksFetched()` и доработать этот метод

**Проверка:**
```bash
./gradlew bootRun
# В логах должен быть 1 запрос вместо N+1
curl http://localhost:8080/api/library/authors
```

---

### 📝 Задание 3: Транзакции

**Файл для изменения:**
- `src/main/kotlin/.../service/LibraryService.kt` — раскомментировать и исправить методы

**Проверка:**
```bash
./gradlew test
# Все тесты должны пройти
```

---

### 🏆 ДЗ: Все выше + Читатели

Необходимо добавить читателей, которые могут брать книги в библиотеке и связать их с книгами many to many. 
Так же должен появиться эндпоинт чтобы получить всех читателей с их книгами.

**Проверка:**
```bash
./gradlew bootRun
curl http://localhost:8080/api/library/readers
# Ответ: [{"id":1,"name":"Иван","email":"ivan@example.com", "books": [{"id":1,"title":"1984","author":...}, ...]}, ...]
```

---

**Удачи! 🚀**
