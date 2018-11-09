package com.example.rafaellat.journaler.preferences

import android.content.Context
import android.content.SharedPreferences

// basic abstraction to provide access to SharePreferences
abstract class PreferencesProviderAbstract {

    abstract fun obtain(configuration: PreferencesConfiguration, ctx: Context): SharedPreferences
}