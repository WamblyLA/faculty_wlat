package org.example
import java.lang.Thread.sleep
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

object CreateThreads {
    fun run(): List<Thread> {
        val threadNames = listOf("Thread-A", "Thread-B", "Thread-C");
        val threads = threadNames.map {name ->
            Thread {
                repeat(5) {
                    println(Thread.currentThread().name)
                    sleep(500);
                }
            }.apply{this.name = name}
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
        repeat(20) {
            execs.submit {
                println(Thread.currentThread().name)
                sleep(200)
            }
        }
        execs.shutdown()
        return listOf();
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
data class User(val name: String)

    val u1 = User("Anton")
    val u2 = User("Anton")

    println(u1 == u2) // true
    println(u1 === u2)

}