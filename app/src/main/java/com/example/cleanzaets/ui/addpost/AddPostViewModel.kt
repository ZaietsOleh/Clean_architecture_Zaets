package com.example.cleanzaets.ui.addpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanzaets.domain.PostInteractor
import javax.inject.Inject

class AddPostViewModel @Inject constructor(
    private val postInteractor: PostInteractor
) : ViewModel() {

    private val _fragmentStateLiveData = MutableLiveData<AddPostState>()
    val fragmentStateLiveData: LiveData<AddPostState> = _fragmentStateLiveData

    fun addPost(rawPost: RawPost) {
        _fragmentStateLiveData.value = postInteractor.addPost(rawPost)
    }
}