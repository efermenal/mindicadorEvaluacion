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
        binding.nameIndicator.text = item.nombre
        binding.codeIndicator.text = item.codigo
        binding.unitIndicator.text = item.unidad_medida
        binding.valueIndicator.text = item.valor.toString()
        val dateString = item.fecha.substring(0, 10)
        binding.dateIndicator.text = dateString

        setSupportActionBarTitle(R.string.title_selected_indicator)

    }
}
