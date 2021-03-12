package ru.mrfiring.shiftweatherapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}