package com.example.cleanzaets.presenter

import com.example.cleanzaets.shared.Result
import com.example.cleanzaets.data.PostRepository
import com.example.cleanzaets.utils.CancelableOperation

class PostPresenter (
    private val postRepository: PostRepository,
    private val postUiMapper: PostUiMapper
    ) {

    private var attachedView: PostView? = null
    private var cancelableOperation: CancelableOperation? = null

    fun attachView(postView: PostView) {
        attachedView = postView

        cancelableOperation = postRepository.getPosts()
            .map(postUiMapper::map)
            .postOnMainThread(::showResult)
    }

    fun detachView() {
        attachedView = null
        cancelableOperation?.cancel()
    }

    private fun showResult(result: Result<List<PostUiModel>, String>) {
        if (result.isError) {
            attachedView?.showError(result.errorResult)
        }
        else {
            attachedView?.showPosts(result.successResult)
        }
    }
}