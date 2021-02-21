package com.example.cleanzaets.data

import android.util.Log
import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.data.datasource.database.PostDao
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.utils.AsyncOperation
import com.example.cleanzaets.utils.Multithreading
import com.example.cleanzaets.utils.POST_LOADING_ERROR
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val multithreading: Multithreading,
    private val postService: PostService,
    private val postDao: PostDao
    ) {

    fun getPosts() : AsyncOperation<Result<List<Post>, String>> {
        return multithreading.async {
            val posts = postDao.getPosts()
            if (posts.isEmpty()) {
                val allPosts: List<Post> = postService.getPosts().execute().body()
                    ?: return@async Result.error(POST_LOADING_ERROR)

                postDao.insertPosts(allPosts.reversed())
            } else {
                return@async Result.success(posts)
            }
            return@async Result.success(postDao.getPosts())
        }
    }

    fun addPost(post: Post) {
        multithreading.async {
            postDao.insertPost(post)
        }.postOnMainThread {}
    }

    fun clear() {
        multithreading.async {
            postDao.clearTable()
        }.postOnMainThread {}
    }
}