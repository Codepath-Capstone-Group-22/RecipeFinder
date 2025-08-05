package com.example.recipefinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter (private val recipeList: List<RecipeItem>, private val listener: OnRecipeInteractionListener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){
    interface OnRecipeInteractionListener {
        fun onRecipeSaved(savedCount: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.nameText)
        val recipeDescription: TextView = view.findViewById(R.id.descriptionText)
        val recipeIngredients: TextView = view.findViewById(R.id.ingredientText)
        val recipeImage: ImageView = view.findViewById(R.id.recipeImage)
        val recipeSaveButton: Button = view.findViewById(R.id.recipeSaveBtn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recipeList[position]
        holder.recipeName.text = item.name
        holder.recipeDescription.text = item.summary
        holder.recipeIngredients.text = item.ingredients
        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.recipeImage.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.recipeImage)
        } else {
            holder.recipeImage.setImageResource(R.drawable.ic_launcher_foreground) // fallback
        }

        holder.recipeSaveButton.setOnClickListener {
            val item = recipeList[position]
            if (!item.saved) {
                item.saved = true
                holder.recipeSaveButton.text = "Saved!"
                val savedCount = recipeList.count { it.saved }
                listener.onRecipeSaved(savedCount)
            } else {
                item.saved = false
                holder.recipeSaveButton.text = "Save"
                val savedCount = recipeList.count { it.saved }
                listener.onRecipeSaved(savedCount)
            }
        }
    }

    override fun getItemCount() = recipeList.size
}