package com.anuj.pockotlin.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.anuj.pockotlin.R
import com.anuj.pockotlin.databinding.ActivityMainBinding
import com.anuj.pockotlin.ui.fragment.MainScreenFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        addMainFragment()
    }

    private fun addMainFragment() {
        val frag = MainScreenFragment.newInstance(null)
        addFragment(frag, binding!!.flFragmentContainer.id)
    }

    private fun addFragment(frag: MainScreenFragment, id: Int, addToBackStack: Boolean = false) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.replace(id, frag, frag.javaClass.simpleName)
        if (addToBackStack)
            fragTransaction.addToBackStack(null)
        fragTransaction.commit()
    }

    fun setScreenTitle(title: String?) {
        binding!!.toolbar.title = title
    }

}
