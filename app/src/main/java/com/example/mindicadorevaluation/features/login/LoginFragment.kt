package com.example.mindicadorevaluation.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.core.utils.ResourceLogin
import com.example.mindicadorevaluation.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.isOn.observe(viewLifecycleOwner) {
            when (it) {
                is ResourceLogin.Loading -> {
                    binding.progressBarSignIn.visibility = View.VISIBLE
                }
                is ResourceLogin.Valid -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                }
                is ResourceLogin.Invalid -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                    Snackbar.make(
                        binding.root,
                        getString(R.string.login_invalid),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResourceLogin.Error -> {
                    binding.progressBarSignIn.visibility = View.INVISIBLE
                    Timber.d("Error Login")
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            val pass = binding.edtPass.text.trim().toString()
            val user = binding.edtUser.text.trim().toString()

            if (pass.isNotEmpty() && user.isNotEmpty()) {
                viewModel.attemptingLogin(id = user, password = pass)
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.user_and_pass_are_empty),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
