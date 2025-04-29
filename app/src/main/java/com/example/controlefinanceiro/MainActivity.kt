package com.example.controlefinanceiro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.expensetracker.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Finish MainActivity so the user can't navigate back to it from LoginActivity
        finish()
    }
}
