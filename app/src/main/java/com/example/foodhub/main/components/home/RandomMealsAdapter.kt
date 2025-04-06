package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.RandomItemBinding
import com.example.foodhub.favourite.FavouritesViewModel
import com.example.foodhub.main.network.data.Meal

class RandomMealsAdapter(
    private var randomMealList: List<Meal>,
    private val favouriteViewModel: FavouritesViewModel
) :
    RecyclerView.Adapter<RandomMealsAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: RandomItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = RandomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return randomMealList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = randomMealList[position]
        holder.apply {
            binding.apply {
                tvMeal.text = meal.strMeal
                tvMealCategory.text = meal.strCategory

                ivMeal.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal)
                    itemView.findNavController().navigate(action)
                }

                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)

                favouriteViewModel.checkIsFavourite(meal.idMeal.toString()) { isFavourite ->
                    if (isFavourite) ivFavouriteToggle.setImageResource(R.drawable.favourite_filled) else ivFavouriteToggle.setImageResource(
                        R.drawable.favourite_border
                    )
                }

                val toggleFavourite: () -> Unit = {
                    favouriteViewModel.setupFavouriteToggle(meal) { isFavourite ->
                        val color =
                            if (isFavourite) R.drawable.favourite_filled else R.drawable.favourite_border
                        ivFavouriteToggle.setImageResource(color)
                    }
                }
                ivFavouriteToggle.setOnClickListener { toggleFavourite() }
            }
        }
    }

    fun setMeals(newRandomMeal : List<Meal>){
        randomMealList = newRandomMeal
        notifyDataSetChanged()
    }
}