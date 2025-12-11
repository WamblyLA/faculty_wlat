package org.example

/**
 * Простой интерфейс для in-memory кеша, хранящего данные в формате ключ-значение.
 */
interface InMemoryCache<K, V> {
    val cash: MutableMap<K, V>
    /**
     * Добавляет пару ключ-значение в кеш.
     *
     * @param key ключ, по которому будет происходить доступ к значению
     * @param value значение, которое нужно сохранить
     * @todo Реализовать добавление элемента в хранилище.
     *       Если ключ уже существует, старое значение должно быть заменено новым.
     */
    fun put(key: K, value: V) {
        cash[key] = value
    }

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ, по которому нужно найти значение
     * @return значение, связанное с ключом, или null, если ключ не найден
     * @todo Реализовать получение значения по ключу.
     *       Если ключ отсутствует в кеше, вернуть null.
     */
    fun get(key: K): V? {
        return cash[key]
    }

    /**
     * Удаляет элемент из кеша по ключу.
     *
     * @param key ключ, который нужно удалить
     * @return true, если элемент был найден и удалён; false — если ключ не существовал
     * @todo Реализовать удаление элемента по ключу.
     *       Вернуть true, если элемент был удалён, иначе — false.
     */
    fun remove(key: K): Boolean {
        return cash.remove(key) != null
    }

    /**
     * Проверяет, содержится ли указанный ключ в кеше.
     *
     * @param key ключ для проверки
     * @return true, если ключ существует в кеше; иначе — false
     * @todo Реализовать проверку наличия ключа.
     *       Убедиться, что проверяется только наличие ключа (без учёта срока жизни и т.п.).
     */
    fun containsKey(key: K): Boolean {
        return cash.containsKey(key)
    }

    /**
     * Очищает весь кеш — удаляет все элементы.
     *
     * @todo Реализовать полную очистку хранилища.
     *       После вызова size() должен вернуть 0, а isEmpty() — true.
     */
    fun clear() {
        cash.clear();
    }

    /**
     * Возвращает количество элементов в кеше.
     *
     * @return текущее число пар ключ-значение
     * @todo Реализовать подсчёт количества элементов в хранилище.
     */
    fun size(): Int {
        return cash.size
    }

    /**
     * Проверяет, пуст ли кеш.
     *
     * @return true, если в кеше нет ни одного элемента; иначе — false
     * @todo Реализовать проверку на пустоту.
     *       Эквивалентно size() == 0, но может быть реализовано эффективнее.
     */
    fun isEmpty(): Boolean {
        return cash.isEmpty()
    }
};
class Cache<K, V>: InMemoryCache<K, V> {
    override val cash = HashMap<K, V>()
    override fun get(key: K): V? {
        return cash[key]
    }
    override fun put(key: K, value: V) {
        cash[key] = value
    }
    override fun remove(key: K): Boolean {
        return cash.remove(key) != null
    }
    override fun containsKey(key: K): Boolean {
        return cash.containsKey(key)
    }
    override fun clear() {
        cash.clear()
    }
    override fun size(): Int {
        return cash.size
    }
    override fun isEmpty(): Boolean {
        return cash.isEmpty()
    }
}