package com.example.mindicadorevaluation.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.databinding.FragmentLoginBinding
import com.example.mindicadorevaluation.features.gone
import com.example.mindicadorevaluation.features.hideKeyBoard
import com.example.mindicadorevaluation.features.show
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels {
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

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            if (viewState.isLoading) {
                binding.progressBarSignIn.show()
            } else {
                binding.progressBarSignIn.gone()
            }
        }

        viewModel.command.observe(viewLifecycleOwner) {
            it?.let {
                processCommand(it)
            }

        }
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            it?.hideKeyBoard()
            val pass = binding.edtPass.text.trim().toString()
            val user = binding.edtUser.text.trim().toString()
            viewModel.attemptingLogin(id = user, password = pass)
        }
    }

    private fun navigateToList() {
        val action = LoginFragmentDirections.actionLoginFragmentToListIndicatorsFragment()
        findNavController().navigate(action)
    }

    private fun processCommand(command: LoginViewModel.Command) {
        when (command) {
            LoginViewModel.Command.EmptyCredentials -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.user_and_pass_are_empty),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            LoginViewModel.Command.NavigateToMainPage -> {
                navigateToList()
            }
            LoginViewModel.Command.InvalidUser -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.login_invalid),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
