package com.example.cleanzaets.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanzaets.R

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {
    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.clFragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.showViewPostsFragment()
    }
}
