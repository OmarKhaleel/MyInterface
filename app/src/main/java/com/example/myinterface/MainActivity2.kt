package com.example.myinterface

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity2 : ComponentActivity() {

    private lateinit var welcomeTV: TextView
    private lateinit var emailED: EditText
    private lateinit var passwordED: EditText
    private lateinit var confirmPasswordED: EditText
    private lateinit var scalar: ProgressBar
    private lateinit var ratingStatus: TextView
    private lateinit var submitBTN: Button
    private lateinit var fadeInAnimation: Animation
    private lateinit var clickAnimation: Animation
    private lateinit var registrationImage: ImageView
    private lateinit var rollingAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        emailED.startAnimation(fadeInAnimation)
        passwordED.startAnimation(fadeInAnimation)
        confirmPasswordED.startAnimation(fadeInAnimation)
        registrationImage.postDelayed({
            registrationImage.startAnimation(rollingAnimation)
        }, 1000)

        submitBTN.setOnClickListener {
            val email: String = emailED.text.toString()

            if (isValidEmail(email) && isValidDomainLength(email)) {
                val password = passwordED.text.toString().trim()

                if (password.isEmpty()) {
                    Toast.makeText(this, "Password is required.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (isPasswordValid(password)) {
                    val confirmPassword: String = confirmPasswordED.text.toString()

                    if (password != confirmPassword) {
                        Toast.makeText(this, "Passwords don't match.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                    submitBTN.startAnimation(clickAnimation)
                    submitBTN.postDelayed({
                        val intent = Intent(this, HomePage::class.java)
                        startActivity(intent)
                    }, 1000)

                } else {
                    Toast.makeText(this, "Password must be 8-20 characters, containing at least 1 small letter, 1 capital letter, 1 number, and 1 special character.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                Toast.makeText(this, "Invalid email or domain length.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        passwordED.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updatePasswordStrength(s?.toString() ?: "")
            }
        })

        confirmPasswordED.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = passwordED.text.toString()
                val confirmPassword = s?.toString() ?: ""
                val passwordStrength = getPasswordStrength(password)

                ratingStatus.text = passwordStrength

                if (password == confirmPassword) {
                    ratingStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark, theme))
                } else {
                    ratingStatus.setTextColor(resources.getColor(android.R.color.holo_red_dark, theme))
                }
            }
        })
    }

    private fun init() {
        welcomeTV = findViewById(R.id.WelcomeTV)
        emailED = findViewById(R.id.EmailED)
        passwordED = findViewById(R.id.PasswordED)
        confirmPasswordED = findViewById(R.id.ConfirmPasswordED)
        scalar = findViewById(R.id.ProgressBar)
        ratingStatus = findViewById(R.id.RatingTV)
        submitBTN = findViewById(R.id.SubmitBTN)
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        clickAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click_animation)
        registrationImage = findViewById(R.id.imageView)
        rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_animation)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^([a-zA-Z0-9_\\.-]+)@([a-zA-Z0-9\\.-]+)\\.([a-zA-Z]{2,})$")
        return email.matches(emailRegex)
    }

    private fun isValidDomainLength(email: String): Boolean {
        val domain = email.substringAfter("@")
        return domain.length in 3..10
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.length < 8 || password.length > 30) {
            return false
        }

        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,20}$".toRegex()
        if (!password.matches(pattern)) {
            return false
        }

        return true
    }

    private fun getPasswordStrength(password: String): String {
        return when {
            password.length < 8 -> {
                updateProgressBar(30)
                "Weak"
            }
            password.length < 15 -> {
                updateProgressBar(70)
                "Medium"
            }
            else -> {
                updateProgressBar(100)
                "Strong"
            }
        }
    }

    private fun updatePasswordStrength(password: String) {
        val passwordStrength = getPasswordStrength(password)
        ratingStatus.text = passwordStrength
    }

    private fun updateProgressBar(progress: Int) {
        scalar.progress = progress
    }
}