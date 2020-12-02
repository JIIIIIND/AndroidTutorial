package com.example.tutorial01_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.roll_button)
        val countUpBtn: Button = findViewById(R.id.count_button)
        var imageId_1: Int = R.drawable.empty_dice
        var imageId_2: Int = R.drawable.empty_dice
        rollButton.setOnClickListener {
            imageId_1 = rollDice(findViewById(R.id.result_img_1))
            imageId_2 = rollDice(findViewById(R.id.result_img_2))
        }
        countUpBtn.setOnClickListener {
            imageId_1 = countUp(imageId_1, findViewById(R.id.result_img_1))
            imageId_2 = countUp(imageId_2, findViewById(R.id.result_img_2))
        }
    }
    private fun getRandomDiceImage() : Int {
        val randomInt = (1..6).random()
        return (when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        })
    }
    private fun rollDice(diceImage : ImageView): Int {
//        Toast.makeText(this, "button clicked",
//        Toast.LENGTH_SHORT).show()
        val drawableResource = getRandomDiceImage()
        diceImage.setImageResource(drawableResource)
        return drawableResource
    }

    private fun countUp(imageId : Int, diceImage : ImageView): Int {
        var changeId = when (imageId) {
            R.drawable.dice_1 -> R.drawable.dice_2
            R.drawable.dice_2 -> R.drawable.dice_3
            R.drawable.dice_3 -> R.drawable.dice_4
            R.drawable.dice_4 -> R.drawable.dice_5
            R.drawable.dice_5 -> R.drawable.dice_6
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(changeId)
        return changeId
    }
}