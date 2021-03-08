package com.example.cleanzaets.domain

import com.example.cleanzaets.data.UserRepository
import com.example.cleanzaets.data.datasource.database.Post
import javax.inject.Inject

class PostModelMapper @Inject constructor(
    private val userRepository: UserRepository
) {
    fun map(postResult: Post): PostModel {
        val badUsers = userRepository.getBadUsers()
        val userStatus: UserStatus? = badUsers.find { it.userId == postResult.userId }?.userStatus

        return PostModel(
            id = postResult.id,
            userId = postResult.userId,
            title = postResult.title,
            body = postResult.body,
            userStatus = userStatus ?: UserStatus.STANDARD
        )
    }
}