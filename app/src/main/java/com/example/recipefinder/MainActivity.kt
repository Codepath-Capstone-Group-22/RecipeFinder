package com.example.recipefinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject
import com.example.recipefinder.BuildConfig





class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private val recipeList = ArrayList<RecipeItem>()
    private lateinit var dietSpinner: Spinner
    val apiKey = "97b3a462e56e42848b7b594bb7a68755"



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("RecipeFinder", "API key: $apiKey")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = RecipeAdapter(recipeList, object : RecipeAdapter.OnRecipeInteractionListener {
            override fun onRecipeSaved(savedCount: Int) {
                updateHeader(savedCount)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val ingredientInput = findViewById<EditText>(R.id.ingredientsText)

        dietSpinner = findViewById(R.id.dietResSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.diet_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dietSpinner.adapter = adapter
        }

        searchButton.setOnClickListener {
            val query = ingredientInput.text.toString()
            val selectedDietRaw = dietSpinner.selectedItem.toString()
            val dietForApi = when (selectedDietRaw) {
                "None" -> null
                "Lacto-Vegetarian" -> "lacto-vegetarian"
                "Ovo-Vegetarian" -> "ovo-vegetarian"
                "Low FODMAP" -> "lowFODMAP"
                else -> selectedDietRaw.lowercase().replace(" ", "")
            }
            fetchRecipes(query, dietForApi)
        }

    }

    private fun fetchRecipes(ingredients: String, diet: String?) {
        val client = AsyncHttpClient()
        val baseUrl = "https://api.spoonacular.com/recipes/complexSearch"
        val urlBuilder = StringBuilder("$baseUrl?fillIngredients=true&addRecipeInformation=true")
        urlBuilder.append("&includeIngredients=${ingredients}")
        if (!diet.isNullOrEmpty()) {
            urlBuilder.append("&diet=${diet}")
        }

        urlBuilder.append("&apiKey=$apiKey")
        val url = urlBuilder.toString()
        Log.d("RecipeFinder", "Request URL: $url")
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("RecipeFinder", "API call success!")
                val root = json.jsonObject
                if (root.has("results") && !root.isNull("results")) {
                    val results = root.getJSONArray("results")
                    recipeList.clear()
                    for (i in 0 until results.length()) {
                        val recipe = results.getJSONObject(i)
                        val id = recipe.getInt("id")
                        val title = recipe.getString("title")
                        val recipeUrl = recipe.getString("spoonacularSourceUrl")
                        val rawSummary = recipe.optString("summary", "No summary available")
                        val summary = HtmlCompat.fromHtml(rawSummary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

                        val imageUrl = recipe.getString("image")
                        val missedIngredientsArray = recipe.optJSONArray("missedIngredients") ?: continue
                        val missedIngredients = mutableListOf<String>()
                        for (j in 0 until missedIngredientsArray.length()) {
                            val ingredient = missedIngredientsArray.getJSONObject(j)
                            missedIngredients.add(ingredient.getString("name"))
                        }
                        val ingredientsSummary = missedIngredients.joinToString(", ")


                        recipeList.add(
                            RecipeItem(
                                id = id,
                                name = title,
                                summary = summary,
                                imageUrl = imageUrl,
                                ingredients = ingredientsSummary,
                                recipeUrl = recipeUrl,
                                saved = false
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.w("RecipeFinder", "No 'results' array found in JSON response.")
                    // Optionally clear list or show "no results" message
                    recipeList.clear()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("RecipeFinder", "API call failed: $errorResponse")
            }
        })
    }

    private fun updateHeader(savedCount: Int) {
        val savedTextView = findViewById<TextView>(R.id.savedText)
        val starImageView = findViewById<ImageView>(R.id.starImage)

        savedTextView.text = savedCount.toString()

        val newStar = if (savedCount > 0) R.drawable.star_filled else R.drawable.star_unfilled
        starImageView.setImageResource(newStar)
    }
}