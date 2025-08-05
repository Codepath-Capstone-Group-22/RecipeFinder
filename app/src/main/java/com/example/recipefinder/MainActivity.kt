package com.example.recipefinder

import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.EditText


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import org.json.JSONObject






class MainActivity : AppCompatActivity() {
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
        rvRecipes.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(recipeList)
        rvRecipes.adapter = recipeAdapter

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val etIngredient = findViewById<EditText>(R.id.etIngredient)

        btnSearch.setOnClickListener {
            val ingredient = etIngredient.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                searchRecipes(ingredient)
            }
        }
    }

    private fun searchRecipes(ingredient: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$ingredient"

        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, response: JSONObject?) {
                recipeList.clear()
                val meals = response?.getJSONArray("meals")
                if (meals != null) {
                    for (i in 0 until meals.length()) {
                        val meal = meals.getJSONObject(i)
                        val recipe = Recipe(
                            meal.getString("strMeal"),
                            meal.getString("strMealThumb"),
                            meal.getString("idMeal")
                        )
                        recipeList.add(recipe)
                    }
                    recipeAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "No results found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseString: String?, throwable: Throwable?) {
                Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
