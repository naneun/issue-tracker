package com.example.issue_tracker.ui.common


import android.content.SharedPreferences

object SharedPreferenceManager {

    lateinit var sharedPreferences: SharedPreferences

    fun initSharedPreferences(sharedPreferences: SharedPreferences){
        this.sharedPreferences = sharedPreferences
    }

    fun putString(key:String, value:String){
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getString(key:String){
        sharedPreferences.getString(key,null)

    }
}