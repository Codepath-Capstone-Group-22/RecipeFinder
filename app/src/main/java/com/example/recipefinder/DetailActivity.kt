package com.example.recipefinder

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvMealName = findViewById<TextView>(R.id.tvMealName)
        val ivMealThumb = findViewById<ImageView>(R.id.ivMealThumb)
        val tvInstructions = findViewById<TextView>(R.id.tvInstructions)

        val mealName = intent.getStringExtra("mealName")
        val mealThumb = intent.getStringExtra("mealThumb")
        val instructions = intent.getStringExtra("instructions")

        Log.d("DetailActivity", "mealName = $mealName")
        Log.d("DetailActivity", "instructions = $instructions")

        tvMealName.text = mealName
        tvInstructions.text = instructions

        Glide.with(this)
            .load(mealThumb)
            .into(ivMealThumb)
    }
}