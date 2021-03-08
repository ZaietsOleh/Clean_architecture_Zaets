package com.example.cleanzaets.ui

import com.example.cleanzaets.R
import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.UserStatus
import com.example.cleanzaets.utils.ResourceRepository
import com.example.cleanzaets.ui.PostUiModel.StandardPost
import com.example.cleanzaets.ui.PostUiModel.BannedPost
import javax.inject.Inject

class PostUiMapper @Inject constructor(private val resourceRepository: ResourceRepository) {
    fun map(postResult: PostModel): PostUiModel {
        val userStatus = postResult.userStatus
        return if (userStatus == UserStatus.BANNED) {
            BannedPost(
                warningText = String.format(
                    resourceRepository.getString(R.string.banned_text),
                    postResult.userId
                ),
                backgroundColor = resourceRepository.getColor(R.color.banned)
            )
        } else {
            StandardPost(
                userId = postResult.userId.toString(),
                title = postResult.title,
                body = postResult.body,
                hasWarning = userStatus == UserStatus.HAS_WARNING,
                backgroundColor = if (userStatus == UserStatus.HAS_WARNING)
                    resourceRepository.getColor(R.color.has_warning)
                else
                    resourceRepository.getColor(R.color.white)
            )
        }
    }
}