package com.example.foodhub.main.components.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.SearchItemBinding
import com.example.foodhub.favourite.FavouritesViewModel
import com.example.foodhub.main.network.data.Meal

class SearchAdapter(
    private var searchList: List<Meal>,
    private val favouritesViewModel: FavouritesViewModel
) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meal = searchList[position]
        holder.apply {
            binding.apply {
                tvMeal.text = meal.strMeal
                tvMealCategory.text = meal.strCategory

                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivMeal)

                ivMeal.setOnClickListener {
                    val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal)
                    itemView.findNavController().navigate(action)
                }

                favouritesViewModel.checkIsFavourite(meal.idMeal.toString()) { isFavourite ->
                    val color =
                        if (isFavourite) R.drawable.favourite_filled else R.drawable.favourite_border
                    ivToggleFavourite.setImageResource(color)
                }

                val toggleFavourite: () -> Unit = {
                    favouritesViewModel.setupFavouriteToggle(meal) { isFavourite ->
                        val color =
                            if (isFavourite) R.drawable.favourite_filled else R.drawable.favourite_border
                        ivToggleFavourite.setImageResource(color)
                    }
                }
                ivToggleFavourite.setOnClickListener { toggleFavourite() }
            }
        }
    }

    fun setSearchList(newSearchList: List<Meal>) {
        searchList = newSearchList
        notifyDataSetChanged()
    }
}