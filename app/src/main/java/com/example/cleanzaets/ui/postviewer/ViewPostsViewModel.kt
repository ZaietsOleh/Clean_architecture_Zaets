package com.example.cleanzaets.ui.postviewer

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.PostUiModel
import com.example.cleanzaets.shared.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewPostsViewModel @Inject constructor(
    private val postInteractor: PostInteractor,
    private val postUiMapper: PostUiMapper
) : ViewModel() {

    val postsLiveData: MutableLiveData<List<PostUiModel>> = MutableLiveData()
    private var onLoadingPostsError: (errorText: String) -> Unit = { }

    @SuppressLint("CheckResult")
    fun getPost() {
        postInteractor.getPosts()
            .map(postUiMapper::map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { posts ->
                if (!posts.isError) {
                    postsLiveData.value = posts.successResult
                }
            }
    }

    fun setOnLoadingPostsError(func: (errorText: String) -> Unit) {
        onLoadingPostsError = func
    }
}