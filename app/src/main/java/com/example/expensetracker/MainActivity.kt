package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var expenseList: ListView
    private lateinit var totalAmount: TextView
    private lateinit var addExpenseButton: Button
    private lateinit var descriptionTextView: TextView

    private val expenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseList = findViewById(R.id.expenseList)
        totalAmount = findViewById(R.id.totalAmount)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        descriptionTextView = findViewById(R.id.descriptionTextView)

        descriptionTextView.text = "Welcome to your personal expense tracker! Add your expenses below and keep track of your spending."

        adapter = ExpenseAdapter(this, expenses)
        expenseList.adapter = adapter

        addExpenseButton.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        loadExpenses()
    }

    private fun loadExpenses() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().getReference("expenses/$userId")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expenses.clear()
                var total = 0.0
                for (expenseSnapshot in snapshot.children) {
                    val expense = expenseSnapshot.getValue(Expense::class.java)
                    expense?.let {
                        expenses.add(it)
                        total += it.amount
                    }
                }
                adapter.notifyDataSetChanged()
                totalAmount.text = "Total: $total"
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}