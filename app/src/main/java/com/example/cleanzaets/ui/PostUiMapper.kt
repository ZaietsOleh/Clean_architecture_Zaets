package com.example.cleanzaets.ui

import com.example.cleanzaets.R
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.UserStatus
import com.example.cleanzaets.utils.ResourceRepository
import com.example.cleanzaets.ui.PostUiModel.StandardPost
import com.example.cleanzaets.ui.PostUiModel.BannedPost
import javax.inject.Inject

class PostUiMapper @Inject constructor(private val resourceRepository: ResourceRepository) {
    fun map(postResult: Result<List<PostModel>, String>): Result<List<PostUiModel>, String> {
        return postResult.mapSuccess { listOfPostModel ->
            listOfPostModel.map { postModel ->
                val userStatus = postModel.userStatus
                if (userStatus == UserStatus.BANNED) {
                    BannedPost (
                        warningText = String.format(resourceRepository.getString(R.string.banned_text), postModel.userId),
                        backgroundColor = resourceRepository.getColor(R.color.banned)
                    )
                } else {
                    StandardPost (
                        userId = postModel.userId.toString(),
                        title = postModel.title,
                        body = postModel.body,
                        hasWarning = userStatus == UserStatus.HAS_WARNING,
                        backgroundColor = if (userStatus == UserStatus.HAS_WARNING)
                            resourceRepository.getColor(R.color.has_warning)
                        else
                            resourceRepository.getColor(R.color.white)
                    )
                }
            }
        }.mapError { resourceRepository.getString(R.string.post_loading_error) }
    }

    fun mapOne(postModel: PostModel): PostUiModel {
        val userStatus = postModel.userStatus
        return if (userStatus == UserStatus.BANNED) {
            BannedPost (
                warningText = String.format(resourceRepository.getString(R.string.banned_text), postModel.userId),
                backgroundColor = resourceRepository.getColor(R.color.banned)
            )
        } else {
            StandardPost (
                userId = postModel.userId.toString(),
                title = postModel.title,
                body = postModel.body,
                hasWarning = userStatus == UserStatus.HAS_WARNING,
                backgroundColor = if (userStatus == UserStatus.HAS_WARNING)
                    resourceRepository.getColor(R.color.has_warning)
                else
                    resourceRepository.getColor(R.color.white)
            )
        }
    }
}