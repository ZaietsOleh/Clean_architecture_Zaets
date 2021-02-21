package com.example.cleanzaets.data.datasource

import com.example.cleanzaets.data.datasource.database.Post
import retrofit2.Call
import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}