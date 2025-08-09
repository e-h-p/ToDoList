package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("todo_prefs", MODE_PRIVATE)
        if (prefs.contains("pin")) {
            startActivity(Intent(this, PinEnterActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_pin)

        val pinInput = findViewById<EditText>(R.id.pinInput)
        val confirmPinBtn = findViewById<Button>(R.id.confirmPinBtn)

        confirmPinBtn.setOnClickListener {
            val pin = pinInput.text.toString()
            if (pin.length == 4) {
                prefs.edit().putString("pin", pin).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Enter a 4-digit PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }
}