package com.example.rafaellat.journaler.activity

import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.databinding.ActivityBindingBinding
import com.example.rafaellat.journaler.model.Note

abstract class BindingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ActivityBindingBinding is auto generated class which name is derived from activity_binding.xom file

        val binding: ActivityBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding)
        val location = Location("dummy")
        val note = Note("my note", "bla", location)
      //  binding.note = note
    }
}