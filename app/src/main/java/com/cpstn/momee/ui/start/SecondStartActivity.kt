package com.cpstn.momee.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpstn.momee.databinding.ActivitySecondStartBinding

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