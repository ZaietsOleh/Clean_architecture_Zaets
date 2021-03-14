package com.example.cleanzaets.data

import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.data.datasource.database.PostDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) {
    suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
        val posts = postDao.getPosts()
        if (posts.isEmpty()) {
            postDao.insertPosts(postService.getPosts().reversed())
            postDao.getPosts()
        }
        posts
    }

    suspend fun addPost(post: Post) = withContext(Dispatchers.IO) {
        postDao.insertPost(post)
    }
}