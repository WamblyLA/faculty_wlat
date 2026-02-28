package com.example.demo1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan
@SpringBootApplication
class Demo1Application {
    @Bean
    fun printer(items: ItemService) = CommandLineRunner {
        items.add("Hello", "World", 123.0)
        println(items.getAllIds())
        items.clear()
        println(items.getAllItems().size)

        items.add("Hello", "World", 123.0)
        val allIds = items.getAllIds()
        items.update(allIds.first(), "new", "name", 235.0)
        println(items.getAllItems())

        items.delete(allIds.first())
        println(items.getAllItems().size)

        items.add("tmp1", "t", 1.0)
        items.add("tmp2", "t", 2.0)

        try {
            items.add("tmp3", "t", 3.0)
        } catch (e: Exception) {
            println(e.message)
        }
        items.clear()
        try {
            items.add("test", "t", 4.0)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}
