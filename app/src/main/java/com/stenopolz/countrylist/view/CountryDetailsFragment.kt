package com.stenopolz.countrylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.stenopolz.countrylist.databinding.FragmentCountryDetailsBinding
import com.stenopolz.countrylist.viewmodel.CountryDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    private var _binding: FragmentCountryDetailsBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "Binding is only valid between onCreateView and onDestroyView"
        }
    private val countryDetailsViewModel: CountryDetailsViewModel by viewModels()

    private val arguments: CountryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = countryDetailsViewModel
        }
        countryDetailsViewModel.fetchCountryDetails(arguments.countryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
