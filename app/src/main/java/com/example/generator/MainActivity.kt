package com.example.generator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MIN_COUNT = 1
        private const val MAX_COUNT = 10_000
        private const val NUMBERS_SEPARATOR = ", "
    }

    private lateinit var etCount: EditText
    private lateinit var etMin: EditText
    private lateinit var etMax: EditText
    private lateinit var tvResult: TextView

    private val generator = UniqueNumberGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        val btnGenerate: Button = findViewById(R.id.btnGenerate)
        btnGenerate.setOnClickListener {
            onGenerateClicked()
        }
    }

    private fun bindViews() {
        etCount = findViewById(R.id.etCount)
        etMin = findViewById(R.id.etMin)
        etMax = findViewById(R.id.etMax)
        tvResult = findViewById(R.id.tvResult)
    }

    private fun onGenerateClicked() {
        val input = readInput()
        if (input == null) {
            showError("Введите корректные целые числа во все поля")
            return
        }

        if (input.count < MIN_COUNT || input.count > MAX_COUNT) {
            showError("Количество чисел должно быть от $MIN_COUNT до $MAX_COUNT")
            return
        }

        if (input.min > input.max) {
            showError("Минимальное значение не может быть больше максимального")
            return
        }

        if (!generator.canGenerate(input.count, input.min, input.max)) {
            showError("В диапазоне от ${input.min} до ${input.max} недостаточно чисел для генерации ${input.count} уникальных значений")
            return
        }

        val numbers = generator.generate(input.count, input.min, input.max)
        displayResult(numbers)
    }

    private fun readInput(): UserInput? {
        val count = etCount.text.toString().trim().toIntOrNull() ?: return null
        val min = etMin.text.toString().trim().toIntOrNull() ?: return null
        val max = etMax.text.toString().trim().toIntOrNull() ?: return null
        return UserInput(count, min, max)
    }

    private fun displayResult(numbers: List<Int>) {
        tvResult.text = numbers.joinToString(separator = NUMBERS_SEPARATOR)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private data class UserInput(val count: Int, val min: Int, val max: Int)
}
