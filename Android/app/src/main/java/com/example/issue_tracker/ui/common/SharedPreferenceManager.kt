package com.example.issue_tracker.ui.common


import android.content.SharedPreferences

object SharedPreferenceManager {

     private var sharedPreferences: SharedPreferences?=null

    fun initSharedPreferences(sharedPreferences: SharedPreferences){
        this.sharedPreferences = sharedPreferences
    }

    fun putString(key:String, value:String){
        sharedPreferences?.let{
            it.edit().putString(key,value).apply()
        }
    }

    fun getString(key:String): String {
        return sharedPreferences?.let{
            it.getString(key,"")
        }?:""

    }
}