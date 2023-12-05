package com.dicoding.savemoney.utils

import androidx.appcompat.app.*

enum class NightMode(val value: Int) {

    AUTO(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),

    ON(AppCompatDelegate.MODE_NIGHT_YES),

    OFF(AppCompatDelegate.MODE_NIGHT_NO)
}