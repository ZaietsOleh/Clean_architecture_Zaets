package com.example.cleanzaets.data.datasource

import com.example.cleanzaets.data.datasource.database.Post
import io.reactivex.Single
import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    fun getPosts(): Single<List<Post>>
}