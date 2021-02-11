package com.example.cleanzaets.presenter

interface PostView {
    fun showPosts(post: List<PostUiModel>)
    fun showError(error: String)
}