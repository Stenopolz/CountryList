package com.stenopolz.countrylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.stenopolz.countrylist.R
import com.stenopolz.countrylist.databinding.FragmentCountryListBinding
import com.stenopolz.countrylist.viewmodel.CountryListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CountryListFragment : Fragment() {

    private var _binding: FragmentCountryListBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "Binding is only valid between onCreateView and onDestroyView"
        }

    private val countryListViewModel: CountryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            adapter = CountriesAdapter {
                countryListViewModel.onCountryIdClicked(it)
            }
            viewModel = countryListViewModel
            list.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                ).apply {
                    AppCompatResources.getDrawable(requireContext(), R.drawable.divider)?.let {
                        setDrawable(it)
                    }
                }
            )
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryListViewModel.countryIdSelected.collect { id ->
                    findNavController().navigate(
                        CountryListFragmentDirections.actionListFragmentToDetailsFragment(id)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
