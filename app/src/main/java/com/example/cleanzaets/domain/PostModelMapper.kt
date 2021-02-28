package com.example.cleanzaets.domain

import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.data.UserRepository
import com.example.cleanzaets.data.datasource.database.Post
import javax.inject.Inject

class PostModelMapper @Inject constructor(
    private val userRepository: UserRepository
) {
    fun map(postResult: Result<List<Post>, String>): Result<List<PostModel>, String> {
        val badUsers = userRepository.getBadUsers()
        return postResult.mapSuccess { listOfPost ->
            listOfPost.map { post ->
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

    fun mapOne(post: Post): PostModel {
        val badUsers = userRepository.getBadUsers()
        val userStatus: UserStatus? = badUsers.find { it.userId == post.userId }?.userStatus

        return PostModel(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body,
            userStatus = userStatus ?: UserStatus.STANDARD
        )
    }
}