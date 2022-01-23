package com.codingnight.example.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingnight.example.viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "Text"
    }
}