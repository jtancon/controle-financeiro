package com.example.controlefinanceiro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Expense(val description: String, val amount: Double)

@Composable
fun FinancialControlScreen() {
    var expenses by remember { mutableStateOf(listOf<Expense>()) }
    var totalAmount by remember { mutableStateOf(0.0) }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    // Para interação com o Gemini
    var geminiPrompt by remember { mutableStateOf("") }
    var geminiResponse by remember { mutableStateOf("") }

    // Atualiza o total sempre que a lista de despesas mudar
    LaunchedEffect(expenses) {
        totalAmount = expenses.sumOf { it.amount }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Texto explicativo
        Text(
            text = "Bem-vindo ao Controle Financeiro! Adicione suas contas e veja o valor total gasto.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campos de entrada para descrição e valor
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.weight(0.5f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Valor") },
                modifier = Modifier.weight(0.5f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para adicionar uma despesa
        Button(
            onClick = {
                val expenseAmount = amount.toDoubleOrNull()
                if (expenseAmount != null && description.isNotBlank()) {
                    expenses = expenses + Expense(description, expenseAmount)
                    description = ""
                    amount = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Adicionar Gasto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibição das despesas
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(expenses) { expense ->
                ExpenseItem(expense)
            }
        }

        // Exibição do total gasto
        Text(
            text = "Total Gasto: R$ ${"%.2f".format(totalAmount)}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
        )

        // Seção de interação com o Gemini
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para a pergunta ao Gemini
        TextField(
            value = geminiPrompt,
            onValueChange = { geminiPrompt = it },
            label = { Text("Perguntar ao Gemini") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para enviar pergunta ao Gemini
        Button(
            onClick = {
                // Simulação de resposta do Gemini (substituir com a integração real)
                geminiResponse = "Resposta simulada do Gemini para: '$geminiPrompt'"
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Enviar Pergunta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibição da resposta do Gemini
        Text(
            text = "Resposta do Gemini: $geminiResponse",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = expense.description,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "R$ ${"%.2f".format(expense.amount)}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinancialControlScreen()
}
