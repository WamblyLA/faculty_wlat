package ru.tbank.education.school.lesson8.practise

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows


class CalculateFinalPriceTest {
    //  1.1
    @Test
    fun `default case`() {
        val result = 1080.0;
        assertEquals(result, calculateFinalPrice(1000.0, 10, 20))
    }
    //  1.2
    @Test
    fun `no discount`() {
        val result = 1200.0;
        assertEquals(result, calculateFinalPrice(1000.0, 0, 20))
    }
    //  1.3
    @Test
    fun `no tax`() {
        val result = 900.0;
        assertEquals(result, calculateFinalPrice(1000.0, 10, 0))
    }
    //  1.4
    @Test
    fun `no params`() {
        val result = 1000.0;
        assertEquals(result, calculateFinalPrice(1000.0, 0, 0))
    }
    //  2.1
    @Test
    fun `negative price`() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateFinalPrice(-1000.0, 10, 20);
        }
    }
    //  2.2
    @Test
    fun `illegal discount`() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateFinalPrice(1000.0, 200, 0);
        }
    }
    //  2.3
    @Test
    fun `illegal tax`() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateFinalPrice(1000.0, 20, -3);
        }
    }
}
/**
 *
 * Сценарии для тестирования:
 *
 * 1. Позитивные сценарии (happy path):
 *    - Обычный случай: basePrice = 1000, discount = 10%, tax = 20% → проверить корректность формулы.
 *    - Без скидки: discountPercent = 0 → итог = basePrice + налог.
 *    - Без налога: taxPercent = 0 → итог = basePrice минус скидка.
 *    - Без скидки и без налога: итог = basePrice.
 *
 * 2. Негативные сценарии (исключения):
 *    - Отрицательная цена: basePrice < 0 → IllegalArgumentException.
 *    - Скидка вне диапазона: discountPercent < 0 или > 100 → IllegalArgumentException.
 *    - Налог вне диапазона: taxPercent < 0 или > 30 → IllegalArgumentException.
 */
