package com.example.mindicadorevaluation.features.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.databinding.FragmentIndicatorDetailBinding
import com.example.mindicadorevaluation.features.setSupportActionBarTitle


class SelectedIndicatorFragment : Fragment(R.layout.fragment_indicator_detail) {

    private val args: SelectedIndicatorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentIndicatorDetailBinding.bind(view)
        val item = args.indicator
        binding.nameIndicator.text = item.name
        binding.codeIndicator.text = item.code
        binding.unitIndicator.text = item.measurementUnit
        binding.valueIndicator.text = item.value.toString()
        val dateString = item.updatedDate.substring(0, 10)
        binding.dateIndicator.text = dateString

        setSupportActionBarTitle(R.string.title_selected_indicator)

    }
}
