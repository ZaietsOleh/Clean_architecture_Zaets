package com.example.cleanzaets.ui.postviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.PostUiModel
import com.example.cleanzaets.shared.Result
import javax.inject.Inject

class ViewPostsViewModel @Inject constructor(
        private val postInteractor: PostInteractor,
        private val postUiMapper: PostUiMapper
) : ViewModel() {

    val postsLiveData: MutableLiveData<List<PostUiModel>> = MutableLiveData()
    private var onLoadingPostsError: (errorText: String) -> Unit = { }

    fun getPost() {
        postInteractor.getPosts()
                .map(postUiMapper::map)
                .postOnMainThread(::updatePostsLiveData)
    }

    private fun updatePostsLiveData(result: Result<List<PostUiModel>, String>) {
        if (result.isError) {
            onLoadingPostsError(result.errorResult)
        } else {
            postsLiveData.value = result.successResult
        }
    }

    fun setOnLoadingPostsError(func: (errorText: String) -> Unit) {
        onLoadingPostsError = func
    }
}