package com.example.scaliotest.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, SearchRepositoriesActivity::class.java)
        startActivity(intent)
        // remove this activity from the stack
        finish()

    }
}