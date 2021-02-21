package com.example.cleanzaets.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanzaets.R
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.di.AppModule
import com.example.cleanzaets.di.DaggerAppComponent
import javax.inject.Inject

class NavigationActivity : AppCompatActivity(R.layout.activity_navigation) {

    val navigator by lazy {
        Navigator(supportFragmentManager, R.id.clFragmentContainer)
    }

    @Inject
    lateinit var postRepository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
        postRepository.clear()
        navigator.showViewPostsFragment()
    }
}