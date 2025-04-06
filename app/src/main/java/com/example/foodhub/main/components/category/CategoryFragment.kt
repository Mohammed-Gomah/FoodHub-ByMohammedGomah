package com.example.foodhub.main.components.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentCategoryBinding
import com.example.foodhub.favourite.FavouritesViewModel
import com.example.foodhub.main.components.category.adapters.AreasAdapter
import com.example.foodhub.main.components.category.adapters.LettersAdapter
import com.example.foodhub.main.components.category.adapters.MealsByAreaAdapter
import com.example.foodhub.main.components.category.adapters.MealsByLetterAdapter
import com.example.foodhub.main.components.home.CategoryAdapter
import com.example.foodhub.main.components.home.HomeViewModel
import com.example.foodhub.main.components.home.MealsAdapter


class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var lettersAdapter: LettersAdapter
    private lateinit var areasAdapter: AreasAdapter
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var mealsByLettersAdapter: MealsByLetterAdapter
    private lateinit var mealsByAreaAdapter: MealsByAreaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeCategories()
        observeMealsByCategory()
        observeMealsByLetters()
        observeAreas()
        observeAreas()
        observeMealsByArea()
    }

    private fun observeCategories() {
        binding.pbCategories.visibility = View.VISIBLE
        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            binding.pbCategories.visibility = View.GONE
            categoryAdapter.setCategories(categories)
        }
        homeViewModel.fetchCategories()
    }


    private fun observeMealsByCategory() {
        binding.pbMealsByCategories.visibility = View.VISIBLE
        homeViewModel.meals.observe(viewLifecycleOwner) { meals ->
            binding.pbMealsByCategories.visibility = View.GONE
            mealsAdapter.setMeals(meals)
        }
        homeViewModel.fetchMealsByCategory("Beef")
    }

    private fun observeMealsByLetters() {
        categoryViewModel.mealByLetter.observe(viewLifecycleOwner) { meals ->
            mealsByLettersAdapter.setMeals(meals)
        }
        categoryViewModel.listMealsByLetters("a")
    }

    private fun observeAreas(){
        categoryViewModel.area.observe(viewLifecycleOwner){areas->
            areasAdapter.setAreas(areas)
        }
        categoryViewModel.listAllAreas()
    }

    private fun observeMealsByArea(){
        categoryViewModel.mealByArea.observe(viewLifecycleOwner){meals->
            mealsByAreaAdapter.setMeals(meals)
        }
        categoryViewModel.listMealsByArea("American")
    }


    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter(listOf(), homeViewModel)
        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        val lettersList = ('a'..'z').toList()
        lettersAdapter = LettersAdapter(lettersList , categoryViewModel)
        binding.rvLetters.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = lettersAdapter
        }

        areasAdapter = AreasAdapter(listOf(),categoryViewModel)
        binding.rvAreas.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = areasAdapter
        }

        mealsAdapter = MealsAdapter(listOf(), favouritesViewModel)
        binding.rvMealsByCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsAdapter
        }

        mealsByLettersAdapter = MealsByLetterAdapter(listOf(), favouritesViewModel)
        binding.rvMealsByLetters.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsByLettersAdapter
        }

        mealsByAreaAdapter = MealsByAreaAdapter(listOf(), favouritesViewModel)
        binding.rvMealsByAreas.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsByAreaAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}