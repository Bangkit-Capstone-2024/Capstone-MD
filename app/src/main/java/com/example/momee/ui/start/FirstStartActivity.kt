package com.example.momee.ui.start

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.momee.R
import com.example.momee.databinding.ActivityFirstStartBinding

class FirstStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstStartBinding
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FirstStartActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            getStartedButton.setOnClickListener {
                SecondStartActivity.start(this@FirstStartActivity)
            }
        }

    }
}