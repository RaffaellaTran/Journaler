package com.example.rafaellat.journaler.preferences

import android.content.Context
import android.content.SharedPreferences

//simple mechanism to obtain shared preferences
class PreferencesProvider : PreferencesProviderAbstract() {

    override fun obtain(configuration: PreferencesConfiguration, ctx: Context): SharedPreferences {
        return ctx.getSharedPreferences(configuration.key, configuration.mode)
    }

}