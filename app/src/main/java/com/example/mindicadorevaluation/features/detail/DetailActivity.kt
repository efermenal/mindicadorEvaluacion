package com.example.mindicadorevaluation.features.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.Repositories.RemoteRepositoryImpl
import com.example.mindicadorevaluation.api.MindicadorApi
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: DetailViewModel

    companion object{
        fun callActivity(context : Context) = Intent(context, DetailActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        setContentView(R.layout.activity_detail)

        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))

    }


    override fun onSupportNavigateUp(): Boolean {
        val nav = findNavController(R.id.nav_host_fragment)
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}