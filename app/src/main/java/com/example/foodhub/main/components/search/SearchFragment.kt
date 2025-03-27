package com.example.foodhub.main.components.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentSearchBinding
import com.example.foodhub.main.network.data.Meal


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupSearchMealByName()
        observeSearchResults()
        setupClearButton()

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapters() {
        searchAdapter = SearchAdapter(listOf())
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun setupSearchMealByName() {
        binding.etSearchView.addTextChangedListener { s ->
            val inputQuery = s.toString().trim()
            if (inputQuery.isNotEmpty()) {
                searchViewModel.searchMealByName(inputQuery)
            } else {
                binding.etSearchView.text.clear()
                showWaitingLogo()
            }
        }
    }


    private fun observeSearchResults() {
        searchViewModel.meals.observe(viewLifecycleOwner) { meals ->
            if (!meals.isNullOrEmpty()) {
                showRecyclerview(meals)
            } else {
                showWaitingLogo()
            }
        }
    }

    private fun showRecyclerview(meals: List<Meal>) {
        binding.tvWaiting.visibility = View.GONE
        binding.ivWaitingLogo.visibility = View.GONE
        binding.rvSearch.visibility = View.VISIBLE
        searchAdapter.setSearchList(meals)
    }

    private fun showWaitingLogo() {
        binding.tvWaiting.visibility = View.VISIBLE
        binding.ivWaitingLogo.visibility = View.VISIBLE
        binding.rvSearch.visibility = View.GONE
        searchAdapter.setSearchList(emptyList())
    }

    private fun setupClearButton() {
        binding.ivClearText.visibility = View.GONE
        binding.ivClearText.setOnClickListener {
            binding.etSearchView.text.clear()
        }

        binding.etSearchView.addTextChangedListener { s ->
            binding.ivClearText.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

    }

    override fun onResume() {
        super.onResume()
        if (binding.etSearchView.text.isNullOrEmpty()) {
            showWaitingLogo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}