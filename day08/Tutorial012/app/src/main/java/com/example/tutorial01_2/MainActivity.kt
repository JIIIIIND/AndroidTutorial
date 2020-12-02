package com.example.tutorial01_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.roll_button)
        val countUpBtn: Button = findViewById(R.id.count_button)
        rollButton.setOnClickListener { rollDice() }
        countUpBtn.setOnClickListener { countUp() }
    }

    private fun rollDice() {
//        Toast.makeText(this, "button clicked",
//        Toast.LENGTH_SHORT).show()
        val resultText: TextView = findViewById(R.id.result_text)
        val randomInt = (1..6).random()
        resultText.text = randomInt.toString()
    }

    private fun countUp() {
        val resultText: TextView = findViewById(R.id.result_text)
        var num = resultText.text.toString().toIntOrNull()
        if (num != 6 && num != null) { num++ }
        resultText.text = num.toString()
    }
}