package com.example.cleanzaets.data

import com.example.cleanzaets.domain.BadUserModel
import com.example.cleanzaets.domain.UserStatus

class UserRepository() {
    companion object {
        private const val FIRST_WARNINGS_USER = 3
        private const val SECOND_WARNINGS_USER = 4
        private const val BANNED_USER = 7
    }


    fun getBadUsers() : Set<BadUserModel> {
        val badUsers = mutableSetOf<BadUserModel>()

        badUsers.add(BadUserModel(FIRST_WARNINGS_USER, UserStatus.HAS_WARNING))
        badUsers.add(BadUserModel(SECOND_WARNINGS_USER, UserStatus.HAS_WARNING))
        badUsers.add(BadUserModel(BANNED_USER, UserStatus.BANNED))

        return badUsers
    }
}