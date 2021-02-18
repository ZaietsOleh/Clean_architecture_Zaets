package com.example.cleanzaets.domain

import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.utils.AsyncOperation
import javax.inject.Inject

class PostInteractor @Inject constructor(
    private val postRepository: PostRepository,
    private val postModelMapper: PostModelMapper
) {

    fun getPosts() : AsyncOperation<Result<List<PostModel>, PostErrors>> {
        return postRepository.getPosts()
                .map(postModelMapper::map)
    }
}