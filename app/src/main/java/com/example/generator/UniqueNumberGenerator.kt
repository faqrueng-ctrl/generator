package com.example.generator

import kotlin.random.Random

/**
 * Генерирует заданное количество уникальных случайных чисел в диапазоне [min, max].
 *
 * Уникальность проверяется через HashSet (добавление и проверка наличия
 * элемента в HashSet выполняется за O(1)), поэтому при каждой генерации
 * нового числа не происходит перебора уже сгенерированных чисел.
 */
class UniqueNumberGenerator {

    /**
     * Сколько различных целых чисел вмещается в диапазон [min, max].
     * Используется Long, чтобы избежать переполнения на больших диапазонах.
     */
    fun rangeSize(min: Int, max: Int): Long {
        return max.toLong() - min.toLong() + 1
    }

    /**
     * Проверяет, что в диапазоне достаточно чисел, чтобы сгенерировать
     * запрошенное количество уникальных значений.
     */
    fun canGenerate(count: Int, min: Int, max: Int): Boolean {
        return rangeSize(min, max) >= count
    }

    /**
     * Генерирует [count] уникальных случайных чисел в диапазоне [min, max].
     * Вызывающая сторона должна предварительно убедиться, что генерация
     * возможна (см. canGenerate), иначе метод выбросит исключение.
     */
    fun generate(count: Int, min: Int, max: Int): List<Int> {
        require(min <= max) { "Минимальное значение не может быть больше максимального" }
        require(canGenerate(count, min, max)) { "В заданном диапазоне недостаточно чисел" }

        val uniqueNumbers = HashSet<Int>(count)
        while (uniqueNumbers.size < count) {
            val candidate = Random.nextInt(min, max + 1)
            uniqueNumbers.add(candidate)
        }

        return uniqueNumbers.toList()
    }
}
