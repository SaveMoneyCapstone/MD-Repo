package com.dicoding.savemoney

enum class ExpensesCategory(val value: String) {
    FOOD_BEVERAGES("Food & Beverages"),
    BILLS_UTILITIES("Bills & Utilities"),
    CLOTHES("Clothes"),
    ENTERTAINMENT("Entertainment"),
    SALARY("Salary"),
    SELLING("Selling"),
    GIFT("Gift"),
    OTHER("OTHER");

    companion object {
        fun getByNumberExpenses(expensesCatNumber: Int) = when (expensesCatNumber) {
            0 -> FOOD_BEVERAGES.value
            1 -> BILLS_UTILITIES.value
            2 -> CLOTHES.value
            3 -> ENTERTAINMENT.value
            4 -> OTHER.value
            else -> FOOD_BEVERAGES.value
        }

        fun getByNumberIncome(incomeCatNumber: Int) = when (incomeCatNumber) {
            0 -> SALARY.value
            1 -> SELLING.value
            2 -> GIFT.value
            3 -> OTHER.value
            else -> SALARY.value
        }
    }
}