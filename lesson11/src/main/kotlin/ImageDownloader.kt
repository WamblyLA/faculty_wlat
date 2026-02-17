package org.example

import kotlinx.coroutines.*
import java.io.File
import java.net.URL
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

data class Info(
    val totalTime: Long,
    val yes: Int,
    val no: Int
)

object ImageDownloader {
    fun run(links: List<String>): Info = runBlocking {
        val dir = File("lesson11/downloads")
        dir.mkdirs()
        val yes = AtomicInteger(0)
        val no = AtomicInteger(0)
        val all = AtomicInteger(0)
        val time = measureTimeMillis {
            supervisorScope {
                val cors = links.mapIndexed { i, link ->
                    async(Dispatchers.IO) {
                        val flag = try {
                            val bytes = URL(link).readBytes()
                            val name = "img_${i}.jpg"
                            File(dir, name).writeBytes(bytes)
                            true
                        } catch (e: Exception) {
                            println("${e.message}")
                            false
                        }
                        if (flag) {
                            yes.incrementAndGet()
                        } else {
                            no.incrementAndGet()
                        }
                        val temp = all.incrementAndGet()
                        println("Downloaded $temp/${links.size}")
                        flag
                    }
                }
                cors.awaitAll()
            }
        }
        Info(
            totalTime = time,
            yes = yes.get(),
            no = no.get(),
        )
    }
}

fun main() {
    val links =
        List(10) { "https://picsum.photos/200/300" } //Эта ссылка падает с HTTP Error: 403, но с другими вроде работает
    val info = ImageDownloader.run(links)
    println(info)
}
