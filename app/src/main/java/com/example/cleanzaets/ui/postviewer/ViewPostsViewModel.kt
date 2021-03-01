package com.example.cleanzaets.ui.postviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleanzaets.R
import com.example.cleanzaets.domain.PostInteractor
import com.example.cleanzaets.ui.PostUiMapper
import com.example.cleanzaets.ui.PostUiModel
import com.example.cleanzaets.ui.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewPostsViewModel @Inject constructor(
    private val postInteractor: PostInteractor,
    private val postUiMapper: PostUiMapper
) : ViewModel() {

    private val _postsLiveData: MutableLiveData<State<List<PostUiModel>, Int>> = MutableLiveData()
    val postsLiveData: LiveData<State<List<PostUiModel>, Int>> = _postsLiveData

    private val compositeDisposable = CompositeDisposable()

    fun getPost() {
        compositeDisposable.add(postInteractor.getPosts()
            .map(postUiMapper::map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { posts ->
                    _postsLiveData.value = State.Content(posts)
                },
                {
                    _postsLiveData.value = State.Error(R.string.post_loading_error)
                }
            )
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}