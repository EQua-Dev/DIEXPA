package com.androidstrike.diexpa.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidstrike.diexpa.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
    }
}