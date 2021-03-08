package com.example.cleanzaets.data

import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.data.datasource.database.PostDao
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) {
    suspend fun getPosts(): List<Post> {
        val posts = postDao.getPosts()
        if (posts.isEmpty()) {
            postDao.insertPosts(postService.getPosts().reversed())
            return postDao.getPosts()
        }
        return posts
    }

    suspend fun addPost(post: Post) {
        postDao.insertPost(post)
    }
}