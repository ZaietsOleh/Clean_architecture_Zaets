package com.example.cleanzaets.utils

import android.content.Context
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.data.PostService
import com.example.cleanzaets.presenter.PostPresenter
import com.example.cleanzaets.ui.PostUiMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostComponent {
    fun createPresenter(context: Context): PostPresenter {
        return PostPresenter(
                postRepository = PostRepository(
                        multithreading = Multithreading(context),
                        postService = createService(),
                ),
                postUiMapper = PostUiMapper(
                        resourceRepository = ResourceRepository(context)
                )
        )
    }

    private fun createService(): PostService {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostService::class.java)
    }
}