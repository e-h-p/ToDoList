package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PinEnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        val prefs = getSharedPreferences("todo_prefs", MODE_PRIVATE)
        val savedPin = prefs.getString("pin", "")

        val pinInput = findViewById<EditText>(R.id.pinInput)
        val confirmPinBtn = findViewById<Button>(R.id.confirmPinBtn)

        confirmPinBtn.setOnClickListener {
            val input = pinInput.text.toString()

            if (input == savedPin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
}