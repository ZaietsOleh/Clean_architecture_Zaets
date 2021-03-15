package com.example.cleanzaets.ui.postviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.PostUiModel
import com.example.cleanzaets.ui.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewPostsViewModel @Inject constructor(
    private val postInteractor: PostInteractor,
    private val postUiMapper: PostUiMapper
) : ViewModel() {

    private val _postsLiveData: MutableLiveData<State<List<PostUiModel>, Int>> = MutableLiveData()
    val postsLiveData: LiveData<State<List<PostUiModel>, Int>> = _postsLiveData

    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts = postInteractor.getPosts()
                .map(postUiMapper::map)
            _postsLiveData.postValue(State.Content(posts))
        }
    }
}
