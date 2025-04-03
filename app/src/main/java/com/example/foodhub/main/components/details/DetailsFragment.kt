package com.example.foodhub.main.components.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodhub.databinding.FragmentDetailsBinding
import com.example.foodhub.main.network.data.Meal

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meal = args.meal
        fetchMealDetails(meal)

    }

    private fun fetchMealDetails(meal: Meal) {
        binding.apply {
            tvMealName.text = meal.strMeal
            tvMealDescription.text = meal.strInstructions
            tvAreaName.text = meal.strArea
            tvCategoryName.text = meal.strCategory

            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .into(ivMealImage)

            ivBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}