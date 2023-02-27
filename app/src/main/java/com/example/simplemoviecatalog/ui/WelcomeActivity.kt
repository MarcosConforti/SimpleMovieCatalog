package com.example.simplemoviecatalog.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.example.simplemoviecatalog.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextActivity.setOnClickListener {
            toTheActivity()
        }
    }
    private fun toTheActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}