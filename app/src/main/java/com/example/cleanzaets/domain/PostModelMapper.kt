package com.example.cleanzaets.domain

import com.example.cleanzaets.data.UserRepository
import com.example.cleanzaets.data.datasource.database.Post
import javax.inject.Inject

class PostModelMapper @Inject constructor(
    private val userRepository: UserRepository
) {
    fun map(postResult: List<Post>): List<PostModel> {
        val badUsers = userRepository.getBadUsers()
        return postResult.map { post ->
            val userStatus: UserStatus? = badUsers.find { it.userId == post.userId }?.userStatus

            PostModel(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body,
                userStatus = userStatus ?: UserStatus.STANDARD
            )
        }
    }
}