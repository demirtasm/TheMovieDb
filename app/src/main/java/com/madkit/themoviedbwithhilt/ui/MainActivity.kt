package com.madkit.themoviedbwithhilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madkit.themoviedbwithhilt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}