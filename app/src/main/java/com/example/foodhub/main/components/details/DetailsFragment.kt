package com.example.foodhub.main.components.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentDetailsBinding
import com.example.foodhub.favourite.FavouritesViewModel
import com.example.foodhub.main.network.data.Meal

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var detailsAdapter: DetailsAdapter

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
        initAdapter(meal)
        initFavouriteToggle(meal)
    }

    private fun initFavouriteToggle(meal: Meal) {

        favouritesViewModel.checkIsFavourite(meal.idMeal.toString()) { isFavourite ->
            val color =
                if (isFavourite) R.drawable.favourite_filled else (R.drawable.favourite_border)
            binding.favouriteToggle.setImageResource(color)
            binding.favouriteToggle0.setImageResource(color)
        }

        val toggleFavourite: () -> Unit = {
            favouritesViewModel.setupFavouriteToggle(meal){isFavourite->
                val color = if (isFavourite) R.drawable.favourite_filled else R.drawable.favourite_border
                binding.favouriteToggle.setImageResource(color)
                binding.favouriteToggle0.setImageResource(color)
            }
        }

        binding.favouriteLayout.setOnClickListener { toggleFavourite() }
        binding.favouriteLayout0.setOnClickListener { toggleFavourite() }

    }



    private fun fetchMealDetails(meal: Meal) {
        binding.apply {
            tvMealName.text = meal.strMeal
            tvMealDescription.text = meal.strInstructions
            tvCategoryName.text = meal.strCategory

            youtubeLink.setOnClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube)).also { startActivity(it) }
            }

            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .into(ivMealImage)

            ivBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }

    private fun Meal.getIngredientList(): List<String> {
        return listOfNotNull(
            strIngredient1,
            strIngredient2,
            strIngredient3,
            strIngredient4,
            strIngredient5,
            strIngredient6,
            strIngredient7,
            strIngredient8,
            strIngredient9,
        ).filter { it.isNotBlank() }
    }

    private fun Meal.getMeasures(): List<String> {
        return listOfNotNull(
            strMeasure1,
            strMeasure2,
            strMeasure3,
            strMeasure4,
            strMeasure5,
            strMeasure6,
            strMeasure7,
            strMeasure8,
            strMeasure9,
        ).filter { it.isNotBlank() }
    }


    private fun initAdapter(meal: Meal) {
        val ingredients = meal.getIngredientList()
        val measures = meal.getMeasures()

        val ingredientWithMeasure = ingredients.mapIndexed { index, ingredient ->
            val measure = if (index < measures.size) measures[index] else ""
            "$measure $ingredient"
        }


        detailsAdapter = DetailsAdapter(ingredientWithMeasure)
        binding.rvIngredients.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = detailsAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}