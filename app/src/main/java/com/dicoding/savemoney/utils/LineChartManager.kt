@file:Suppress("SameParameterValue")

package com.dicoding.savemoney.utils

import android.graphics.*
import android.util.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.google.firebase.auth.*
import com.google.firebase.firestore.*
import java.util.Date

@Suppress("SameParameterValue")
class LineChartManager(private val lineChart: LineChart) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private fun createLineDataSet(data: List<Float>, label: String, color: Int): LineDataSet {
        // Create entries for the LineDataSet
        val entries = data.mapIndexed { index, value -> Entry(index.toFloat(), value) }

        // Create LineDataSet
        val dataSet = LineDataSet(entries, label)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = color
        dataSet.circleRadius = 5f
        dataSet.valueTextSize = 5f
        dataSet.setCircleColor(color)

        return dataSet
    }

    fun setupLineChart() {
        val userId = firebaseAuth.currentUser?.uid

        if (userId != null) {
            fetchChartData(userId) { dataIncome, dataExpense, timestamps ->
                // Create LineDataSet for income
                val incomeLineDataSet =
                    createLineDataSet(dataIncome, "Income", Color.GREEN)

                // Create LineDataSet for expense
                val expenseDataSet =
                    createLineDataSet(dataExpense, "Expense", Color.RED)

                // Configure Y-axis to display values as integers
                val leftAxis = lineChart.axisLeft
                leftAxis.valueFormatter = CustomYAxisValueFormatter()

                // Configure X-axis to display dates
                val xAxis = lineChart.xAxis
                xAxis.valueFormatter = CustomXAxisValueFormatter(timestamps)

                xAxis.labelCount = 1
                xAxis.position = XAxis.XAxisPosition.BOTTOM

                // Configure legend
                val legend = lineChart.legend
                legend.isEnabled = true
                legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                legend.setDrawInside(false)

                lineChart.axisRight.isEnabled = false

                lineChart.description.isEnabled = false

                // Set LineData to the chart
                lineChart.data = LineData(incomeLineDataSet, expenseDataSet)

                // Animate the chart
                lineChart.animateXY(100, 500)

                lineChart.axisLeft.textColor = Color.LTGRAY
                lineChart.axisLeft.textSize = 12F
                lineChart.axisRight.textColor = Color.LTGRAY
                lineChart.legend.textColor = Color.LTGRAY
                lineChart.xAxis.textColor = Color.LTGRAY
                lineChart.xAxis.textSize = 12F
                lineChart.description.textColor = Color.LTGRAY
            }
        }
    }

    private fun fetchChartData(
        userId: String,
        callback: (List<Float>, List<Float>, List<Date>) -> Unit
    ) {
        // Fetch data from Firebase and call the callback
        val incomesRef = firestore.collection("users").document(userId).collection("incomes").orderBy("date", Query.Direction.ASCENDING)
        val expensesRef = firestore.collection("users").document(userId).collection("expense").orderBy("date", Query.Direction.ASCENDING)

        val dataIncome = mutableListOf<Float>()
        val dataExpense = mutableListOf<Float>()
        val timestamps = mutableListOf<Date>()

        incomesRef.get().addOnSuccessListener { incomesSnapshot ->
            for (incomeDocument in incomesSnapshot) {
                val amount = incomeDocument.getDouble("amount") ?: 0.0
                val category = incomeDocument.getString("category")
                val timestamp = incomeDocument.getTimestamp("date")
                dataIncome.add(amount.toFloat())
                timestamps.add(timestamp?.toDate() ?: Date())
            }

            expensesRef.get().addOnSuccessListener { expensesSnapshot ->
                for (expenseDocument in expensesSnapshot) {
                    val amount = expenseDocument.getDouble("amount") ?: 0.0
                    val timestamp = expenseDocument.getTimestamp("date")
                    dataExpense.add(amount.toFloat())
                    timestamps.add(timestamp?.toDate() ?: Date())
                }

                callback.invoke(dataIncome, dataExpense, timestamps)
            }
        }.addOnFailureListener { exception ->
            // Handle failure
            Log.e("LineChartManager", "Error fetching data: ${exception.message}")
        }
    }

}
