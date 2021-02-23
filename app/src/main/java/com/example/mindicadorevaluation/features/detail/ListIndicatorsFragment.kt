package com.example.mindicadorevaluation.features.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.utils.Constants.BUNDLE_INDICATOR
import com.example.mindicadorevaluation.core.utils.Resource
import com.example.mindicadorevaluation.databinding.FragmentListIndicatorsBinding
import com.example.mindicadorevaluation.features.detail.adapters.IndicatorAdapter
import com.example.mindicadorevaluation.features.login.MainActivity
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class ListIndicatorsFragment : Fragment() {

    lateinit var indicatorAdapter : IndicatorAdapter

    lateinit var viewModel: DetailViewModel
    private var _binding: FragmentListIndicatorsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireActivity() is DetailActivity){
            viewModel = (activity as DetailActivity).viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListIndicatorsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeList()
        viewModel.getIndicators()
        val user = viewModel.getUserName().toUpperCase()

        if (requireActivity() is AppCompatActivity){
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_detail, user)
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
        //super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_login -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(R.string.exit_warning)
                    .setPositiveButton(R.string.exit_warning_positive, DialogInterface.OnClickListener{ dialogInterface, i ->
                        requireContext().startActivity(MainActivity.callActivity(requireContext()))
                        requireActivity().finish()

                    })
                    .setNegativeButton(R.string.exit_warning_negative, DialogInterface.OnClickListener { dialogInterface, i ->  
                        
                    })

                val b = builder.create()
                b.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeList() {

        viewModel.responseApi.removeObservers(viewLifecycleOwner)
        viewModel.responseApi.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgress()
                }

                is Resource.Success -> {
                    hideProgress()
                    response.data?.let { indicatorResponse ->
                        val listIndicators = indicatorResponse.getListIndicator() as List<Indicator>
                        //indicatorAdapter.differ.submitList(listIndicators.toList())
                        indicatorAdapter.updateList(listIndicators.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgress()

                    Snackbar.make(
                        this.requireView(),
                        getString(R.string.load_error),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
                is Resource.NoInternet -> {
                    hideProgress()
                    Snackbar.make(
                        this.requireView(),
                        getString(R.string.no_internet),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }


    private fun init() {
       indicatorAdapter = IndicatorAdapter()

        binding.rvIndicators.apply {
            adapter = indicatorAdapter
            layoutManager = LinearLayoutManager(activity)
           setHasFixedSize(true)
        }

        indicatorAdapter.setOnIndicatorClickListener {
            val action = ListIndicatorsFragmentDirections.actionListIndicatorsFragmentToSelectedIndicatorFragment(it)
            findNavController().navigate(action)
        }


        binding.rvIndicators.setOnClickListener {
            findNavController().navigate(R.id.action_listIndicatorsFragment_to_selectedIndicatorFragment)
        }
    }

    private fun hideProgress() {
        binding.pbRequestApi.visibility =View.INVISIBLE
    }

    private fun showProgress() {
        binding.pbRequestApi.visibility =View.VISIBLE
    }
}