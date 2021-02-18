package com.example.cleanzaets.data

import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.domain.PostModelMapper
import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.utils.AsyncOperation
import com.example.cleanzaets.utils.Multithreading
import javax.inject.Inject

class PostRepository @Inject constructor(
        private val multithreading: Multithreading,
        private val postService: PostService,
    ) {

    fun getPosts() : AsyncOperation<Result<List<Post>, PostErrors>> {
        return multithreading.async {
            val allPosts: List<Post>? = postService.getPosts().execute().body()

            allPosts?.let {
                return@async Result.success(it)
            }
            return@async Result.error(PostErrors.POST_LOADING_ERROR)
        }
    }
}