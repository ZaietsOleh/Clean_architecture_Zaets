package com.example.cleanzaets.domain

import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.data.Post
import com.example.cleanzaets.data.UserRepository

class PostModelMapper {
    fun map(postResult: Result<List<Post>, PostErrors>): Result<List<PostModel>, PostErrors> {
        val badUsers = UserRepository().getBadUsers()
        return postResult.mapSuccess { listOfPost ->
            listOfPost.map { post ->
                val userStatus: UserStatus? = badUsers.find{ it.userId == post.userId }?.userStatus

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
}