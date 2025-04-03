package com.example.foodhub.favourite

import android.content.Intent
import android.icu.text.Transliterator.Position
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.FavouritesItemBinding
import com.example.foodhub.main.network.data.Meal

class FavouritesAdapter(private var favouritesList: List<Meal> ,private val favouritesViewModel: FavouritesViewModel): RecyclerView.Adapter<FavouritesAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding : FavouritesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int , meal: Meal){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = FavouritesItemBinding.inflate(LayoutInflater.from(parent.context) , parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position,favouritesList[position])
    }

    fun setFavourites(newFavouritesList: List<Meal>){
        favouritesList = newFavouritesList
        notifyDataSetChanged()
    }
}