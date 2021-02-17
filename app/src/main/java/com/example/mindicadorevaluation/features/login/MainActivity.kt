package com.example.mindicadorevaluation.features.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.core.utils.ResourceLogin
import com.example.mindicadorevaluation.databinding.ActivityMainBinding
import com.example.mindicadorevaluation.features.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
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

        viewModel.isOn.observe(this, Observer {
            when(it){
                is ResourceLogin.Loading -> {
                    binding.progressBarSignIn.visibility = View.VISIBLE
                }
                is ResourceLogin.Valid -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                    this.startActivity(DetailActivity.callActivity(this))
                    finish()
                }
                is ResourceLogin.Invalid -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                    Snackbar.make(view, getString(R.string.login_invalid), Snackbar.LENGTH_SHORT).show()
                }
                is ResourceLogin.Error -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                    Timber.d("Error Login")
                }
            }
        })

        binding.btnLogin.setOnClickListener {
            val pass = binding.edtPass.text.trim().toString()
            val user = binding.edtUser.text.trim().toString()

            if (pass.isNotEmpty() && user.isNotEmpty()){
                viewModel.attemptingLogin(id= user, password = pass)
            }else{
                Snackbar.make(view, getString(R.string.user_and_pass_are_empty), Snackbar.LENGTH_SHORT).show()
            }
        }

    }

}