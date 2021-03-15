package com.example.cleanzaets.domain

data class PostModel (
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val userStatus: UserStatus
    )
