package com.example.momee.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.momee.R
import com.example.momee.databinding.ActivityFirstStartBinding
import com.example.momee.databinding.ActivitySecondStartBinding

class SecondStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondStartBinding
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SecondStartActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            getStartedButton.setOnClickListener {
                ThirdStartActivity.start(this@SecondStartActivity)
            }
        }

    }
}