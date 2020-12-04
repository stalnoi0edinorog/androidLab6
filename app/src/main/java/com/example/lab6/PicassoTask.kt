package com.example.lab6

import android.os.Bundle
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class PicassoTask: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            Picasso.get().load(URL).into(binding.image)
            binding.button.visibility = INVISIBLE
        }
    }
}