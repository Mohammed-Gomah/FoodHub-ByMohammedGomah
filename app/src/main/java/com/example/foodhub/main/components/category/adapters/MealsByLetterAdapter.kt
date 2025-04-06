package com.example.foodhub.main.components.category.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.MealItemBinding
import com.example.foodhub.favourite.FavouritesViewModel
import com.example.foodhub.main.network.data.Meal

class MealsByLetterAdapter(
    private var mealList: List<Meal>,
    private val favouritesViewModel: FavouritesViewModel
) : RecyclerView.Adapter<MealsByLetterAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = mealList[position]

        holder.apply {
            binding.apply {
                tvMealName.text = meal.strMeal ?: "not found"
                category.text = meal.strCategory ?: "not found"
                area.text = meal.strArea ?: "not found"
                layerWatchVideo.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
                    itemView.context.startActivity(intent)
                }



                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)

                favouritesViewModel.checkIsFavourite(meal.idMeal.toString()) { isFavourite ->
                    if (isFavourite) ivFavouriteToggle.setImageResource(R.drawable.favourite_filled) else ivFavouriteToggle.setImageResource(
                        R.drawable.favourite_border
                    )
                }

                ivFavouriteToggle.setOnClickListener {
                    favouritesViewModel.setupFavouriteToggle(meal) { isFavourite ->
                        if (isFavourite) ivFavouriteToggle.setImageResource(R.drawable.favourite_filled) else ivFavouriteToggle.setImageResource(
                            R.drawable.favourite_border
                        )
                    }
                }

            }
        }
    }

    fun setMeals(newMealList: List<Meal>) {
        mealList = newMealList
        notifyDataSetChanged()
    }
}