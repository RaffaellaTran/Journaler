package com.example.rafaellat.journaler.fragment

import com.example.rafaellat.journaler.R

class ItemsFragment: BaseFragment() {
    override val logTag: String = "Item fragment"
    override fun getLayout(): Int {
        return R.layout.fragment_items
    }
}