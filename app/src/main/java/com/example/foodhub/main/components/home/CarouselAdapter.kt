package com.example.foodhub.main.components.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.databinding.RandomsShapeItemBinding
import com.example.foodhub.main.network.data.Meal

class CarouselAdapter(private var mealImageList: List<Meal>) :
    RecyclerView.Adapter<CarouselAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView){
            val image : ImageView = itemView.findViewById(R.id.randomShapeImageView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.randoms_shape_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val mealImage = mealImageList[position]

        Glide.with(holder.itemView.context)
                .load(mealImage.strMealThumb)
                .into(holder.image)

    }

    override fun getItemCount(): Int {
        return mealImageList.size
    }

    fun setRandomMeals(newRanImages:List<Meal>){
        mealImageList = newRanImages
        notifyDataSetChanged()
    }
}