package com.example.foodhub.main.components.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentHomeBinding
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var randomMealsAdapter: RandomMealsAdapter
    private lateinit var carouselAdapter: CarouselAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeCategories()
        observeMealsByCategory()
        fetchRandomMeals()

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
        homeViewModel.meals.observe(viewLifecycleOwner) { meals ->
            mealsAdapter.setMeals(meals)
        }
        homeViewModel.fetchMealsByCategory("Beef")
    }

    private fun fetchRandomMeals() {
        homeViewModel.randomMeals.observe(viewLifecycleOwner) { meals ->
            randomMealsAdapter.setMeals(meals)
            carouselAdapter.setRandomMeals(meals.take(5))
            setupIndicators(meals.take(5).size) // إنشاء المؤشرات بناءً على عدد العناصر
        }
        homeViewModel.fetchRandomMeals()
    }


    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter(listOf(), homeViewModel)
        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        mealsAdapter = MealsAdapter(listOf())
        binding.rvMeals.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mealsAdapter
        }

        randomMealsAdapter = RandomMealsAdapter(listOf())
        binding.rvRandomMeals.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = randomMealsAdapter
        }


        carouselAdapter = CarouselAdapter(listOf())
        binding.rvRandoms.apply {
            setHasFixedSize(true)
            layoutManager = CarouselLayoutManager()
            adapter = carouselAdapter
        }
        CarouselSnapHelper().attachToRecyclerView(binding.rvRandoms)
        val snapHelper = LinearSnapHelper()

        binding.rvRandoms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as CarouselLayoutManager
                    val snapView = snapHelper.findSnapView(layoutManager)
                    val activePosition = snapView?.let { layoutManager.getPosition(it) } ?: RecyclerView.NO_POSITION

                    if (activePosition != RecyclerView.NO_POSITION) {
                        updateIndicators(activePosition, binding.indicatorLayout.children.toList())
                    }
                }
            }
        })


    }

    private fun setupIndicators(count: Int) {
        binding.indicatorLayout.removeAllViews() //remove any old views

        val indicators = mutableListOf<View>()

        for (i in 0 until count){
            val indicator = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(48 , 8).apply {
                    setMargins(8,0,8,0)
                }
                setBackgroundResource(R.drawable.indicator_inactive) // default background
            }
            binding.indicatorLayout.addView(indicator)
            indicators.add(indicator)
        }

        updateIndicators(0,indicators)
    }


    private fun updateIndicators(activePosition: Int, indicators: List<View>) {
        for (i in indicators.indices) {
            if (i == activePosition) {
                indicators[i].setBackgroundResource(R.drawable.indicator_active)
            } else {
                indicators[i].setBackgroundResource(R.drawable.indicator_inactive)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}