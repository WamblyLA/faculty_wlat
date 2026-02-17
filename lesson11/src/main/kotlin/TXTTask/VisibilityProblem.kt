/**
 *
 * Проблема:
 * При оптимизации компилятор и процессор могут переупорядочивать операции
 * или кешировать переменные в регистрах процессора. Это приводит к тому,
 * что изменения переменной в одном потоке могут быть не видны в другом потоке.
 *
 */
class VisibilityProblem {
    @Volatile
    private var running = true
    fun startWriter(): Thread {
        return Thread {
            // Имитация работы
            repeat(100) {
                Thread.sleep(10)
                Thread.yield()
            }

            running = false
            println("Writer: установил running = false (изменение может быть не видно)")
        }
    }
    fun startReader(): Thread {
        return Thread {
            println("Reader: начал работу (ждет running = false)")

            while (running) {

            }

            println("Reader: завершил работу (увидел running = false)")
        }
    }
}