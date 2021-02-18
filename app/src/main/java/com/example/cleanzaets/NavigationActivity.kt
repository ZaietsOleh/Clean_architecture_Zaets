package com.example.cleanzaets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerApplication

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.clFragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.showViewPostsFragment()
    }
}