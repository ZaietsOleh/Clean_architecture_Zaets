package com.example.cleanzaets.domain

import com.example.cleanzaets.R
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.ui.addpost.AddPostState
import com.example.cleanzaets.ui.addpost.RawPost
import com.example.cleanzaets.utils.ResourceRepository
import java.util.*
import javax.inject.Inject

class PostInteractor @Inject constructor(
    private val postRepository: PostRepository,
    private val postModelMapper: PostModelMapper,
    private val resourceRepository: ResourceRepository
) {
    companion object {
        private const val MINIMUM_TITLE_LENGTH = 3
        private const val MAXIMUM_TITLE_LENGTH = 50
        private const val MINIMUM_BODY_LENGTH = 5
        private const val MAXIMUM_BODY_LENGTH = 500
        private const val ID = 1
        private const val USER_ID = 5
    }

    suspend fun getPosts(): List<PostModel> {
        return postRepository.getPosts()
            .map(postModelMapper::map)
    }

    suspend fun addPost(post: RawPost): AddPostState {
        val title = post.title
        val body = post.body
        when {
            title.length < MINIMUM_TITLE_LENGTH || title.length > MAXIMUM_TITLE_LENGTH -> return AddPostState.TITLE_ERROR_LENGTH
            hasBadWord(title) -> return AddPostState.TITLE_ERROR_BAD_WORD
            body.length < MINIMUM_BODY_LENGTH || body.length > MAXIMUM_BODY_LENGTH -> return AddPostState.BODY_ERROR_LENGTH
        }

        postRepository.addPost(
            Post(
                id = ID,
                userId = USER_ID,
                title = title,
                body = body
            )
        )

        return AddPostState.WITHOUT_ERROR
    }

    private fun hasBadWord(text: String): Boolean {
        val lowerCaseText = text.toLowerCase(Locale.ROOT)
        return when {
            lowerCaseText.contains(resourceRepository.getString(R.string.bad_word_ad)) -> true
            lowerCaseText.contains(resourceRepository.getString(R.string.bad_word_buy)) -> true
            lowerCaseText.contains(resourceRepository.getString(R.string.bad_word_product)) -> true
            else -> false
        }
    }
}