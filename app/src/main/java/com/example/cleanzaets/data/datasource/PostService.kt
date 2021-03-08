package com.example.cleanzaets.data.datasource

import com.example.cleanzaets.data.datasource.database.Post
import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}