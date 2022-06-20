package com.example.issue_tracker.ui.login

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.issue_tracker.MainActivity
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.lifecycleOwner= this

        binding.tvLoginBtnTitle.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}