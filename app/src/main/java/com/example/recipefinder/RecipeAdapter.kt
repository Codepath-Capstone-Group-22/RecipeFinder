package com.example.recipefinder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class RecipeAdapter(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMeal: TextView = itemView.findViewById(R.id.tvMealName)
        val ivMeal: ImageView = itemView.findViewById(R.id.ivMealThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.tvMeal.text = recipe.strMeal

        Glide.with(holder.ivMeal.context)
            .load(recipe.strMealThumb)
            .into(holder.ivMeal)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val idMeal = recipe.idMeal
            val lookupUrl = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$idMeal"
            val client = AsyncHttpClient()

            client.get(lookupUrl, object : JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    val response = json.jsonObject
                    val mealsArray = response.getJSONArray("meals")
                    if (mealsArray != null && mealsArray.length() > 0) {
                        val mealDetails = mealsArray.getJSONObject(0)
                        val instructions = mealDetails.getString("strInstructions")

                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("mealName", recipe.strMeal)
                            putExtra("mealThumb", recipe.strMealThumb)
                            putExtra("instructions", instructions)
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "No details found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(context, "Failed to load recipe details", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    override fun getItemCount(): Int = recipeList.size
}
