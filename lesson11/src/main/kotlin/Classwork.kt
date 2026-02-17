package org.example

import java.lang.Thread.sleep
import java.math.BigInteger
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.Collections.synchronizedList
import java.util.concurrent.Callable
import kotlinx.coroutines.*
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
        repeat(20) { index ->
            execs.submit {
                val msg = "Thread $index with name ${Thread.currentThread().name}"
                println(msg)
                result.add(msg);
                sleep(200)
            }
        }
        execs.shutdown()
        return listOf();
    }
}

object FutureFactorial {
    fun run(): Map<Int, BigInteger> {
        val execs = Executors.newFixedThreadPool(4);
        val futures = mutableMapOf<Int, java.util.concurrent.Future<BigInteger>>()
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
    fun run(): Long = runBlocking {
        TODO()
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

}