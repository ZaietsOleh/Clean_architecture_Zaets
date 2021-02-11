package com.example.cleanzaets.presenter

import com.example.cleanzaets.R
import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.UserStatus
import com.example.cleanzaets.shared.PostErrors
import com.example.cleanzaets.utils.ResourceRepository
import com.example.cleanzaets.presenter.PostUiModel.NormalPost
import com.example.cleanzaets.presenter.PostUiModel.BannedPost

class PostUiMapper(private val resourceRepository: ResourceRepository) {
    fun map(postResult: Result<List<PostModel>, PostErrors>): Result<List<PostUiModel>, String> {
        return postResult.mapSuccess { listOfPostModel ->
            listOfPostModel.map { postModel ->
                when (postModel.userStatus) {
                    UserStatus.NORMAL -> NormalPost (
                        userId = postModel.userId.toString(),
                        title = postModel.title,
                        body = postModel.body,
                        hasWarning = false,
                        backgroundColor = resourceRepository.getColor(R.color.normal_post)
                    )
                    UserStatus.HAS_WARNING -> NormalPost (
                        userId = postModel.userId.toString(),
                        title = postModel.title,
                        body = postModel.body,
                        hasWarning = true,
                        backgroundColor = resourceRepository.getColor(R.color.has_warning)
                    )
                    UserStatus.BANNED -> BannedPost (
                        userId = postModel.userId.toString(),
                        backgroundColor = resourceRepository.getColor(R.color.banned)
                    )
                }
            }
        }.mapError { resourceRepository.getString(R.string.post_loading_error) }
    }
}