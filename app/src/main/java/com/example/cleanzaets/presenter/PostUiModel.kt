package com.example.cleanzaets.presenter

import androidx.annotation.ColorInt

sealed class PostUiModel {
    data class NormalPost(
        val userId: String,
        val title: String,
        val body: String,
        val hasWarning: Boolean,
        @ColorInt
        val backgroundColor: Int
    ) : PostUiModel()

    data class BannedPost(
        val userId: String,
        @ColorInt
        val backgroundColor: Int
    ) : PostUiModel()
}