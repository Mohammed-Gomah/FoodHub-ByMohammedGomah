package com.example.foodhub.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Grid
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var favouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupFavourites()
    }

    private fun setupAdapters() {
        favouritesAdapter = FavouritesAdapter(emptyList() , favouritesViewModel)
        val layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvFavourites.apply {
            this.layoutManager = layoutManager
            adapter = favouritesAdapter
        }
    }

    private fun setupFavourites() {
        favouritesViewModel.favouritesList.observe(viewLifecycleOwner) { favourites ->
            favouritesAdapter.setFavourites(favourites)
        }
        favouritesViewModel.getFavourites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}