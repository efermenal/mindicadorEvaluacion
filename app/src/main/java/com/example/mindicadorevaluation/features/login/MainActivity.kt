package com.example.mindicadorevaluation.features.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mindicadorevaluation.databinding.ActivityMainBinding
import com.example.mindicadorevaluation.features.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    companion object{
        fun callActivity(context : Context) = Intent(context, MainActivity::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
          this.startActivity(DetailActivity.callActivity(this))
          finish()
        }

    }

}