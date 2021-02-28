package com.example.cleanzaets.data

import com.example.cleanzaets.data.datasource.PostService
import com.example.cleanzaets.data.datasource.database.Post
import com.example.cleanzaets.data.datasource.database.PostDao
import com.example.cleanzaets.shared.Result
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) {
    fun getPosts(): Observable<Result<List<Post>, String>> {
        return postDao.getPosts()
            .map { posts ->
                if (posts.isEmpty()) {
                    postService.getPosts()
                        .subscribeOn(Schedulers.io())
                        .subscribe { postsFromRemoteSource ->
                            postDao.insertPosts(postsFromRemoteSource.reversed())
                        }
                }
                Result.success(posts)
            }
    }


    fun addPost(post: Post) {
        postDao.insertPost(post)
    }

    fun clear() {
        postDao.clearTable()
    }
}