package com.example.cleanzaets.ui

import androidx.fragment.app.Fragment

abstract  class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigation: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }
}