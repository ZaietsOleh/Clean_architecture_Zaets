package com.example.cleanzaets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanzaets.databinding.ActivityPostsBinding

class PostsActivity : AppCompatActivity() {
    private val binding: ActivityPostsBinding by lazy {
        ActivityPostsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
    }
}