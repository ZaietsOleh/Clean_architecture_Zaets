package com.example.cleanzaets.domain

import com.example.cleanzaets.data.UserRepository
import com.example.cleanzaets.data.datasource.database.Post
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class PostModelMapperTest {
    private val mockUserRepository = mockk<UserRepository>() {
        every { getBadUsers() } returns setOf(
            BadUserModel(
                userId = 3,
                userStatus = UserStatus.HAS_WARNING
            ),
            BadUserModel(
                userId = 4,
                userStatus = UserStatus.HAS_WARNING
            ),
            BadUserModel(
                userId = 7,
                userStatus = UserStatus.BANNED
            )
        )
    }
    private val postModelMapper = PostModelMapper(mockUserRepository)

    @TestFactory
    fun `map Post to PostModel if user has warnings`(): List<DynamicTest> {
        return listOf(3, 4).map { userId ->
            DynamicTest.dynamicTest("if userId $userId postModel has warning") {
                val testingPost = Post(
                    id = 1,
                    userId = userId,
                    body = "body",
                    title = "title"
                )
                val correctResult = PostModel(
                    id = 1,
                    userId = userId,
                    body = "body",
                    title = "title",
                    userStatus = UserStatus.HAS_WARNING
                )


                postModelMapper.map(testingPost) shouldBe correctResult
            }
        }
    }

    @Test
    fun `map Post to PostModel if user banned`() {
        val testingPost = Post(
            id = 1,
            userId = 7,
            body = "body",
            title = "title"
        )
        val correctResult = PostModel(
            id = 1,
            userId = 7,
            body = "body",
            title = "title",
            userStatus = UserStatus.BANNED
        )

        postModelMapper.map(testingPost) shouldBe correctResult
    }
}