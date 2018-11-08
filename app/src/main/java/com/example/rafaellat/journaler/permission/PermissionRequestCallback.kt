package com.example.rafaellat.journaler.permission

//permission request callback
interface PermissionRequestCallback {
    fun onPermissionGranted(permissions: List<String>)
    fun onPermissionDenied(permissions: List<String>)
}