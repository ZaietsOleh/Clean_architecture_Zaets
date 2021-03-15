package com.example.cleanzaets.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.cleanzaets.ui.addpost.AddPostFragment
import com.example.cleanzaets.ui.postviewer.ViewPostsFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val ADD_POST_FRAGMENT = "ADD_POST_FRAGMENT"
    }


    fun showAddPostFragment() {
        fragmentManager.beginTransaction()
            .add(container, AddPostFragment.newInstance())
            .addToBackStack(ADD_POST_FRAGMENT)
            .commit()
    }

    fun showViewPostsFragment() {
        fragmentManager.beginTransaction()
            .replace(container, ViewPostsFragment.newInstance())
            .commit()
    }
}
