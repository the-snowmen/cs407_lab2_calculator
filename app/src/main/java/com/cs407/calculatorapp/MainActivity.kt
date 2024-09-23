package com.cs407.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var firstNumberEditText: EditText
    private lateinit var secondNumberEditText: EditText
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var divideButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstNumberEditText = findViewById(R.id.firstNumberEditText)
        secondNumberEditText = findViewById(R.id.secondNumberEditText)
        addButton = findViewById(R.id.addButton)
        subtractButton = findViewById(R.id.subtractButton)
        multiplyButton = findViewById(R.id.multiplyButton)
        divideButton = findViewById(R.id.divideButton)

        // Set click listeners
        addButton.setOnClickListener { performOperation(getString(R.string.addition_symbol)) }
        subtractButton.setOnClickListener { performOperation(getString(R.string.subtraction_symbol)) }
        multiplyButton.setOnClickListener { performOperation(getString(R.string.multiplication_symbol)) }
        divideButton.setOnClickListener { performOperation(getString(R.string.division_symbol)) }
    }

    private fun performOperation(operator: String) {
        val firstNumberStr = firstNumberEditText.text.toString()
        val secondNumberStr = secondNumberEditText.text.toString()

        // Check for any empty input
        if (firstNumberStr.isEmpty() || secondNumberStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_both_numbers), Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val firstNumber = firstNumberStr.toInt()
            val secondNumber = secondNumberStr.toInt()
            var result = 0

            when (operator) {
                getString(R.string.addition_symbol) -> result = firstNumber + secondNumber
                getString(R.string.subtraction_symbol) -> result = firstNumber - secondNumber
                getString(R.string.multiplication_symbol) -> result = firstNumber * secondNumber
                getString(R.string.division_symbol) -> {
                    if (secondNumber == 0) {
                        Toast.makeText(this, getString(R.string.cannot_divide_by_zero), Toast.LENGTH_SHORT).show()
                        return
                    } else {
                        result = firstNumber / secondNumber
                    }
                }
            }

            // Run CalculatorActivity to display the result
            val intent = Intent(this, CalculatorActivity::class.java)
            intent.putExtra("RESULT", result)
            startActivity(intent)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
        }
    }
}
