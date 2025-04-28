package com.example.expensetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var expenseNameEditText: EditText
    private lateinit var expenseAmountEditText: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        expenseNameEditText = findViewById(R.id.expenseNameEditText)
        expenseAmountEditText = findViewById(R.id.expenseAmountEditText)
        addButton = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            addExpense()
        }
    }

    private fun addExpense() {
        val expenseName = expenseNameEditText.text.toString().trim()
        val expenseAmountString = expenseAmountESditText.text.toString().trim()

        if (expenseName.isEmpty() || expenseAmountString.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val expenseAmount = expenseAmountString.toDoubleOrNull()
        if (expenseAmount == null) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("expenses")

        val expenseId = myRef.push().key
        if (expenseId != null) {
            val expenseItem = ExpenseItem(expenseName, expenseAmount)
            myRef.child(expenseId).setValue(expenseItem)
                .addOnSuccessListener {
                    Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show()
        }
    }
}