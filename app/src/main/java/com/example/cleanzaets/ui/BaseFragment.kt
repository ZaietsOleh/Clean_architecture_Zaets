package com.example.cleanzaets.ui

import androidx.fragment.app.Fragment
import com.example.cleanzaets.NavigationActivity
import com.example.cleanzaets.Navigator

abstract  class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigation: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }
}