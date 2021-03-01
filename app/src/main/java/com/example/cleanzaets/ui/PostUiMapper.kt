package com.example.cleanzaets.ui

import com.example.cleanzaets.R
import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.UserStatus
import com.example.cleanzaets.utils.ResourceRepository
import com.example.cleanzaets.ui.PostUiModel.StandardPost
import com.example.cleanzaets.ui.PostUiModel.BannedPost
import javax.inject.Inject

class PostUiMapper @Inject constructor(private val resourceRepository: ResourceRepository) {
    fun map(postResult: List<PostModel>): List<PostUiModel> {
        return postResult.map { post ->
            val userStatus = post.userStatus
            if (userStatus == UserStatus.BANNED) {
                BannedPost(
                    warningText = String.format(
                        resourceRepository.getString(R.string.banned_text),
                        post.userId
                    ),
                    backgroundColor = resourceRepository.getColor(R.color.banned)
                )
            } else {
                StandardPost(
                    userId = post.userId.toString(),
                    title = post.title,
                    body = post.body,
                    hasWarning = userStatus == UserStatus.HAS_WARNING,
                    backgroundColor = if (userStatus == UserStatus.HAS_WARNING)
                        resourceRepository.getColor(R.color.has_warning)
                    else
                        resourceRepository.getColor(R.color.white)
                )
            }
        }
    }
}