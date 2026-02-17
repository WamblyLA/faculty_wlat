package org.example.MDTask

import java.lang.Thread.sleep
import java.math.BigInteger
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.Collections.synchronizedList
import java.util.concurrent.Callable
import kotlinx.coroutines.*
import java.io.File
import java.util.concurrent.Future

object CreateThreads {
    fun run(): List<Thread> {
        val threadNames = listOf("Thread-A", "Thread-B", "Thread-C");
        val threads = threadNames.map { name ->
            Thread {
                repeat(5) {
                    println(Thread.currentThread().name)
                    sleep(500);
                }
            }.apply { this.name = name }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        return threads
    }
}

object RaceCondition {
    fun run(): Int {
        var counter = 0;
        val threads = (1..10).map {
            Thread {
                repeat(1000) {
                    counter++;
                }
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        return counter
    }
}

object SynchronizedCounter {
    fun run(): Int {
        var counter = 0;
        val lock = Any()
        val threads = (1..10).map {
            Thread {
                repeat(1000) {
                    synchronized(lock) {
                        counter++;
                    }
                }
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        return counter
    }

    fun runAtomic(): AtomicInteger {
        var counter = AtomicInteger(0);
        val threads = (1..10).map {
            Thread {
                repeat(1000) {
                    counter.incrementAndGet();
                }
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        return counter
    }
}

object Deadlock {
    fun runDeadlock() {
        val first = Any()
        val second = Any()
        val threadF = Thread {
            synchronized(first) {
                println("Лишь раз")
                sleep(100);
                synchronized(second) {
                    println("Готово")
                }
            }
        }
        val threadS = Thread {
            synchronized(second) {
                println("Лишь два")
                sleep(100);
                synchronized(first) {
                    println("Готово")
                }
            }
        }
        threadF.start()
        threadS.start()
        threadS.join()
        threadF.join()
        println("Не завершится")
    }

    fun runFixed(): Boolean {
        val first = Any()
        val second = Any()
        val threadF = Thread {
            synchronized(first) {
                println("Лишь раз")
                sleep(100);
                synchronized(second) {
                    println("Готово")
                }
            }
        }
        val threadS = Thread {
            synchronized(first) {
                println("Лишь два")
                sleep(100);
                synchronized(second) {
                    println("Готово")
                }
            }
        }
        threadF.start()
        threadS.start()
        threadS.join()
        threadF.join()
        println("Завершено")
        return true;
    }
}

object ExecutorServiceExample {
    fun run(): List<String> {
        val execs = Executors.newFixedThreadPool(4);
        val result = synchronizedList(mutableListOf<String>());
        repeat(20) { i ->
            execs.submit {
                val msg = "Поток $i с именем ${Thread.currentThread().name}"
                println(msg)
                result.add(msg);
                sleep(200)
            }
        }
        execs.shutdown()
        return result;
    }
}

object FutureFactorial {
    fun run(): Map<Int, BigInteger> {
        val execs = Executors.newFixedThreadPool(4);
        val futures = mutableMapOf<Int, Future<BigInteger>>()
        for (n in 1..10) {
            val future =
                execs.submit(Callable {
                    var ans = BigInteger.ONE
                    for (i in 2..n) {
                        ans = ans.multiply(BigInteger.valueOf(i.toLong()))
                    }
                    ans
                })
            futures[n] = future
        }
        val result = futures.mapValues { (_, fut) -> fut.get() }
        execs.shutdown()
        return result
    }
}

object CoroutineLaunch {
    fun run(): List<String> = runBlocking {
        val results = synchronizedList(mutableListOf<String>())
        val cors = listOf(
            launch(CoroutineName("CorA")) {
                repeat(5) {
                    val msg = coroutineContext[CoroutineName]!!.name
                    println(msg)
                    results.add(msg)
                    delay(500)
                }
            },
            launch(CoroutineName("CorB")) {
                repeat(5) {
                    val msg = coroutineContext[CoroutineName]!!.name
                    println(msg)
                    results.add(msg)
                    delay(500)
                }
            },
            launch(CoroutineName("CorC")) {
                repeat(5) {
                    val msg = coroutineContext[CoroutineName]!!.name
                    println(msg)
                    results.add(msg)
                    delay(500)
                }
            },
        )
        cors.joinAll()
        results
    }
}

object AsyncAwait {
    private fun summa(a: Long, b: Long): Long {
        var ans = 0L
        for (i in a..b) {
            ans += i
        }
        return ans
    }

    fun run(): Long = runBlocking {
        val n = 1000000L;
        val p = n / 4
        val a1 = async { summa(1, p) }
        val a2 = async { summa(p + 1, 2 * p) }
        val a3 = async { summa(2 * p + 1, 3 * p) }
        val a4 = async { summa(3 * p + 1, 4 * p) }
        a1.await() + a2.await() + a3.await() + a4.await()
    }
}

object StructuredConcurrency {
    fun run(failingCoroutineIndex: Int): Int = runBlocking {
        var ans = AtomicInteger(0)
        try {
            coroutineScope {
                repeat(5) { i ->
                    launch {
                        if (i == failingCoroutineIndex) {
                            throw RuntimeException("Упала корутина $i")
                        }
                        try {
                            repeat(10) {
                                delay(10)
                            }
                            ans.incrementAndGet()
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        } catch (e: Exception) {
        }
        ans.get()
    }
}

object WithContextIO {
    fun run(filePaths: List<String>): Map<String, String> = runBlocking {
        val ans = filePaths.map { elem ->
            async {
                val text = withContext(Dispatchers.IO) {
                    File(elem).readText()
                }
                elem to text
            }
        }
        ans.associate { it.await() }
    }
}

fun main() {
//    CreateThreads.run()
//    println(RaceCondition.run())
//    println(SynchronizedCounter.runAtomic())
//    println(SynchronizedCounter.run())
//    Deadlock.runDeadlock()
//    Deadlock.runFixed()
//    ExecutorServiceExample.run()
//    println(FutureFactorial.run())
//    CoroutineLaunch.run()
//    println(AsyncAwait.run())
//    println(StructuredConcurrency.run(1))
//    println(
//        WithContextIO.run(
//            listOf(
//                "lesson11/test_files/file1.txt",
//                "lesson11/test_files/file2.txt",
//                "lesson11/test_files/file3.txt"
//            )
//        )
//    )

}