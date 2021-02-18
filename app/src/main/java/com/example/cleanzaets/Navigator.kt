package com.example.cleanzaets

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cleanzaets.ui.BaseFragment
import com.example.cleanzaets.ui.addpost.AddPostFragment
import com.example.cleanzaets.ui.postviewer.ViewPostsFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    fun showAddPostFragment() {
        fragmentManager.beginTransaction()
            .add(container, AddPostFragment.newInstance())
            .commit()
    }

    fun showViewPostsFragment() {
        fragmentManager.beginTransaction()
            .replace(container, ViewPostsFragment.newInstance())
            .commit()
    }
}