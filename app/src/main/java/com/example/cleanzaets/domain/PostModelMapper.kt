package com.example.cleanzaets.domain

import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.data.Post

class PostModelMapper {
    companion object {
        private const val FIRST_WARNINGS_USER = 3
        private const val SECOND_WARNINGS_USER = 4
        private const val BANNED_USER = 7
    }

    fun map(postResult: Result<List<Post>, PostErrors>): Result<List<PostModel>, PostErrors> {
        return postResult.mapSuccess { listOfPost ->
            listOfPost.map { post ->
                val userStatus = when (post.id) {
                    FIRST_WARNINGS_USER, SECOND_WARNINGS_USER -> UserStatus.HAS_WARNING
                    BANNED_USER -> UserStatus.BANNED
                    else -> UserStatus.NORMAL
                }

                PostModel(
                    id = post.id,
                    userId = post.userId,
                    title = post.title,
                    body = post.body,
                    userStatus = userStatus
                )
            }
        }
    }
}