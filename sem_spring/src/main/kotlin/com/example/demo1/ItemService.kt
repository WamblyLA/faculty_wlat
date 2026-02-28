package com.example.demo1

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
fun generateId(): String {
    val usable = ('a'..'z') + ('1'..'9')
    return (1..10).map { usable.random() }.joinToString("")
}

@Service
class ItemService(private val given: GivenProperties) {
    private val log = LoggerFactory.getLogger(ItemService::class.java)

    private fun impossible(name: String) = given.impossibleNames.any {it.trim().equals(name.trim(), ignoreCase = true)}
    private val items = mutableMapOf<String, Item>()
    fun get(id: String): Item? = items[id]
    fun add(name: String, info: String, price: Double) {
        if (items.size >= given.maxCnt) {
            log.error("Impossible to add: limit reached")
            throw IllegalArgumentException("Limit reached")
        }
        if (impossible(name)) {
            log.error("Impossible to add: name is not allowed")
            throw IllegalArgumentException("Name is not allowed")
        }
        val id = generateId()
        val item = Item(id = id, name = name, info = info, price = price)
        items[id] = item
        log.info("Added item with such params")
    }

    fun delete(id: String) {
        log.info("Element was removed")
        items.remove(id)
    }

    fun update(id: String, name: String?, info: String?, price: Double?) {
            val prevItem = items[id] ?: run {
                log.error("This id does not exist"); throw IllegalArgumentException("Element with $id is impossible to update")
            }
            items[id] = Item(id, name ?: prevItem.name, info ?: prevItem.info, price ?: prevItem.price)
            log.info("Element was updated")
    }

    fun getItem(id: String): Item? = items[id];
    fun getAllItems(): List<Item> = items.values.toList()
    fun getAllIds(): Set<String> = items.keys
    fun clear() = items.clear()
}
