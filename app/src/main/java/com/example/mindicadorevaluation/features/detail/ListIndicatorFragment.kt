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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import com.example.mindicadorevaluation.features.show
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ListIndicatorFragment : Fragment() {

    lateinit var indicatorAdapter: IndicatorAdapter

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeList()
        val user = viewModel.getUserName().uppercase()

        if (requireActivity() is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.title_detail, user)
        }

        binding.svIndicator.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                indicatorAdapter.filterByCode(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                indicatorAdapter.filterByCode(newText!!)
                return true
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_activity, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(R.string.exit_warning)
                    .setPositiveButton(
                        R.string.exit_warning_positive
                    ) { _, _ ->
                        requireContext().startActivity(MainActivity.callActivity(requireContext()))
                        requireActivity().finish()

                    }
                    .setNegativeButton(
                        R.string.exit_warning_negative
                    ) { _, _ ->

                    }

                val b = builder.create()
                b.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeList() {

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState.isLoading) {
                true -> binding.pbRequestApi.show()
                false -> binding.pbRequestApi.gone()
            }

            indicatorAdapter.updateList(viewState.indicators)

            viewModel.command.observe(viewLifecycleOwner) {
                it?.let { processCommand(it) }
            }

        }
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
        }
    }

    private fun init() {
        indicatorAdapter = IndicatorAdapter()

        binding.rvIndicators.apply {
            adapter = indicatorAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        indicatorAdapter.setOnIndicatorClickListener {
            val action =
                ListIndicatorFragmentDirections
                    .actionListIndicatorsFragmentToSelectedIndicatorFragment(it)
            findNavController().navigate(action)
        }

    }

}
