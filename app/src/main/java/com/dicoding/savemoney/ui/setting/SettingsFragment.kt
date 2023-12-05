package com.dicoding.savemoney.ui.setting

import android.os.*
import androidx.appcompat.app.*
import androidx.preference.*
import com.dicoding.savemoney.R
import com.dicoding.savemoney.utils.*


class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
setPreferencesFromResource(R.xml.root_preference, rootKey)
        val switchTheme: ListPreference = findPreference(getString(R.string.pref_key_dark))!!
        switchTheme.setOnPreferenceChangeListener { _, newValue ->
            val stringValue = newValue.toString()
            if (stringValue == getString(R.string.pref_dark_auto)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    updateTheme(NightMode.AUTO.value)
                } else {
                    updateTheme(NightMode.ON.value)
                }
            } else if (stringValue == getString(R.string.pref_dark_off)) {
                updateTheme(NightMode.OFF.value)
            } else {
                updateTheme(NightMode.ON.value)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}