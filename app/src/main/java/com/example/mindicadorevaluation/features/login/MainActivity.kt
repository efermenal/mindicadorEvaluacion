package com.example.mindicadorevaluation.features.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.databinding.ActivityMainBinding
import com.example.mindicadorevaluation.features.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel : MainActivityViewModel

    companion object{
        fun callActivity(context : Context) = Intent(context, MainActivity::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            val pass = binding.edtPass.text.toString()
            val user = binding.edtUser.text.toString()

            if (pass != "" && user != ""){
                this.startActivity(DetailActivity.callActivity(this))
                finish()
            }else{
                Snackbar.make(view, getString(R.string.user_and_pass_are_empty), Snackbar.LENGTH_SHORT).show()
            }
        }

    }

}