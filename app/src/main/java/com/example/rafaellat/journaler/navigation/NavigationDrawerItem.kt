package com.example.rafaellat.journaler.navigation

import android.content.Context

data class NavigationDrawerItem(
    val title: String,
    val onClick: Runnable,
    var enabled: Boolean= true
)