package com.example.recipefinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


    class LoginActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            val usernameInput = findViewById<EditText>(R.id.usernameInput)
            val passwordInput = findViewById<EditText>(R.id.passwordInput)
            val loginButton = findViewById<Button>(R.id.loginButton)

            loginButton.setOnClickListener {
                val username = usernameInput.text.toString()
                val password = passwordInput.text.toString()


                if (username.isNotEmpty() && password.isNotEmpty()) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }