package com.cpstn.momee.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpstn.momee.databinding.ActivityThirdStartBinding
import com.cpstn.momee.ui.auth.login.LoginActivity

class ThirdStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdStartBinding
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ThirdStartActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            getStartedButton.setOnClickListener {
                LoginActivity.start(this@ThirdStartActivity)
            }
        }

    }
}