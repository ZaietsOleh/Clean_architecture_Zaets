package com.example.cleanzaets.ui

import com.example.cleanzaets.domain.PostModel
import com.example.cleanzaets.domain.UserStatus
import com.example.cleanzaets.utils.ResourceRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class PostUiMapperTest {
    private val backgroundColor = 4
    private val warningText = "warningText"
    private val mockResourceRepository = mockk<ResourceRepository>() {
        every { getColor(any()) } returns backgroundColor
        every { getString(any()) } returns warningText
    }
    private val postUiMapper = PostUiMapper(mockResourceRepository)

    @Test
    fun `map PostModel to PostUiModel if user has warnings`() {
        val testingPost = PostModel(
            id = 1,
            userId = 1,
            body = "body",
            title = "title",
            userStatus = UserStatus.HAS_WARNING
        )

        val correctResult = PostUiModel.StandardPost(
            userId = "1",
            body = "body",
            title = "title",
            hasWarning = true,
            backgroundColor = backgroundColor
        )

        postUiMapper.map(testingPost) shouldBe correctResult
    }

    @Test
    fun `map PostModel to PostUiModel if user has not warnings`() {
        val testingPost = PostModel(
            id = 1,
            userId = 1,
            body = "body",
            title = "title",
            userStatus = UserStatus.STANDARD
        )

        val correctResult = PostUiModel.StandardPost(
            userId = "1",
            body = "body",
            title = "title",
            hasWarning = false,
            backgroundColor = backgroundColor
        )

        postUiMapper.map(testingPost) shouldBe correctResult
    }

    @Test
    fun `map PostModel to PostUiModel if user banned`() {
       val testingPost = PostModel(
            id = 1,
            userId = 1,
            body = "body",
            title = "title",
            userStatus = UserStatus.BANNED
        )

        val correctResult = PostUiModel.BannedPost(
            warningText = warningText,
            backgroundColor = backgroundColor
        )

        postUiMapper.map(testingPost) shouldBe correctResult
    }
}