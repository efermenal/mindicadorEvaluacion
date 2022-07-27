package com.example.mindicadorevaluation.features.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.databinding.FragmentListIndicatorBinding
import com.example.mindicadorevaluation.features.detail.adapters.IndicatorAdapter
import com.example.mindicadorevaluation.features.gone
import com.example.mindicadorevaluation.features.login.MainActivity
import com.example.mindicadorevaluation.features.setSupportActionBarTitle
import com.example.mindicadorevaluation.features.show
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ListIndicatorFragment : Fragment(R.layout.fragment_list_indicator) {

    private lateinit var indicatorAdapter: IndicatorAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ListIndicatorViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentListIndicatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListIndicatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
        init()
        observeList()
        viewModel.getIndicators()

        binding.svIndicator.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setInputSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setInputSearch(newText ?: "")
                return true
            }
        })

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeList() {

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            render(viewState)
        }
        viewModel.command.observe(viewLifecycleOwner) {
            it?.let { processCommand(it) }
        }
    }

    private fun render(viewState: ListIndicatorViewModel.ViewState) {
        when (viewState.isLoading) {
            true -> binding.pbRequestApi.show()
            false -> binding.pbRequestApi.gone()
        }
        setSupportActionBarTitle(R.string.title_detail, viewState.userName)
        indicatorAdapter.submitList(viewState.selectedIndicators)
        binding.svIndicator.setQuery(viewState.indicatorInput, false)
    }

    private fun processCommand(command: ListIndicatorViewModel.Command) {
        when (command) {
            ListIndicatorViewModel.Command.Error -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.load_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            ListIndicatorViewModel.Command.NoInternet -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.no_internet),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            ListIndicatorViewModel.Command.LogOut -> navigateToLogin()
        }
    }

    private fun init() {
        indicatorAdapter = IndicatorAdapter {
            val action =
                ListIndicatorFragmentDirections
                    .actionListIndicatorsFragmentToSelectedIndicatorFragment(it)
            findNavController().navigate(action)
        }

        binding.rvIndicators.apply {
            adapter = indicatorAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

    }

    private fun navigateToLogin() {
        requireContext().startActivity(MainActivity.callActivity(requireContext()))
        requireActivity().finish()
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_detail_activity, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_login -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage(R.string.exit_warning)
                            .setPositiveButton(
                                R.string.exit_warning_positive
                            ) { _, _ ->
                                viewModel.logout()
                            }
                            .setNegativeButton(
                                R.string.exit_warning_negative
                            ) { _, _ ->

                            }.create().show()
                    }

                }
                return true
            }

        })
    }

}
