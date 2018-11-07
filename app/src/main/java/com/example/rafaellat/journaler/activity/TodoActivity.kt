package com.example.rafaellat.journaler.activity

import com.example.rafaellat.journaler.R

class TodoActivity: ItemActivity() {
    override val tag: String
        get() = "TodoActivity"

    override fun getLayout() = R.layout.activity_todo
}