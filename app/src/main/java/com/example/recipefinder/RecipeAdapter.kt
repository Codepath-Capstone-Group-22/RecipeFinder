package com.example.recipefinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter (private val recipeList: List<RecipeItem>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.nameText)
        val recipeDescription: TextView = view.findViewById(R.id.descriptionText)
        val recipeIngredients: TextView = view.findViewById(R.id.ingredientText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recipeList[position]
        holder.recipeName.text = item.name
        holder.recipeDescription.text = item.description
        holder.recipeIngredients.text = item.ingredients
    }

    override fun getItemCount() = recipeList.size
}