package com.anuj.pockotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

import com.anuj.pockotlin.ui.fragment.MainScreenFragment

class MainScreenFragmentFactory(private val callback: MainScreenFragment.IdlingCallback) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return MainScreenFragment(callback)
    }
}
