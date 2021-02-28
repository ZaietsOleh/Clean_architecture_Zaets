package com.example.cleanzaets.ui.addpost

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanzaets.domain.PostInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    private val postInteractor: PostInteractor
) : ViewModel() {

    private val _fragmentStateLiveData = MutableLiveData<AddPostState>()
    val fragmentStateLiveData: LiveData<AddPostState> = _fragmentStateLiveData

    @SuppressLint("CheckResult")
    fun addPost(rawPost: RawPost) {
        Observable.just(rawPost)
            .map { postInteractor.addPost(rawPost) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                _fragmentStateLiveData.value = state
            }
    }
}