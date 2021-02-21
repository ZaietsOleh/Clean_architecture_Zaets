package com.example.cleanzaets.domain

import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.ui.addpost.AddPostState
import com.example.cleanzaets.ui.addpost.RawPost
import com.example.cleanzaets.utils.AsyncOperation
import java.util.*
import javax.inject.Inject

class PostInteractor @Inject constructor(
    private val postRepository: PostRepository,
    private val postModelMapper: PostModelMapper
) {

    fun getPosts() : AsyncOperation<Result<List<PostModel>, String>> {
        return postRepository.getPosts()
                .map(postModelMapper::map)
    }

    fun addPost(post: RawPost) : AddPostState {
        val title = post.title
        val body = post.body
        when {
            title.length < 2 || title.length > 50 -> return AddPostState.TITLE_ERROR_LENGTH
            hasBadWord(title) -> return AddPostState.TITLE_ERROR_BAD_WORD
            body.length < 5 || body.length > 500 -> return AddPostState.BODY_ERROR_LENGTH
        }

        postRepository.addPost(
            Post(
            id = 1,
            userId = 5,
            title = title,
            body = body
        )
        )

        return AddPostState.WITHOUT_ERROR
    }

    private fun hasBadWord(text: String) : Boolean {
        val lowerCaseText = text.toLowerCase(Locale.ROOT)
        return when {
            lowerCaseText.contains("реклама") -> true
            lowerCaseText.contains("товар") -> true
            lowerCaseText.contains("куплю") -> true
            else -> false
        }
    }
}