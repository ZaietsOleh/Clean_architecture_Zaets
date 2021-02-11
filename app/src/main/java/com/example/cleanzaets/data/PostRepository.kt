package com.example.cleanzaets.data

import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.PostModelMapper
import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.utils.AsyncOperation
import com.example.cleanzaets.utils.Multithreading
import com.example.cleanzaets.utils.PostService

class PostRepository (
    private val multithreading: Multithreading,
    private val postService: PostService,
    private val postModelMapper: PostModelMapper
    ) {

    fun getPosts() : AsyncOperation<Result<List<PostModel>, PostErrors>> {
        val asyncOperation = multithreading.async<Result<List<Post>, PostErrors>> {
            val allPosts: List<Post>? = postService.getPosts().execute().body()

            allPosts?.let {
                return@async Result.success(it)
            }
            return@async Result.error(PostErrors.POST_LOADING_ERROR)
        }

        return asyncOperation.map(postModelMapper::map)
    }
}