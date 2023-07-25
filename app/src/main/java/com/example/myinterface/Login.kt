package com.example.myinterface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailED: EditText = findViewById(R.id.EmailED)
        val passwordED: EditText = findViewById(R.id.PasswordED)
        val loginBTN: Button = findViewById(R.id.LoginBTN)

        loginBTN.setOnClickListener {
            val intent = Intent(this, ToDoList::class.java)
            startActivity(intent)
        }
    }
}