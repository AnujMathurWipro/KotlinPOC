package com.anuj.pockotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.anuj.pockotlin.R
import com.anuj.pockotlin.adapter.MainScreenListAdapter
import com.anuj.pockotlin.databinding.FragmentMainBinding
import com.anuj.pockotlin.models.BaseResult
import com.anuj.pockotlin.models.Response
import com.anuj.pockotlin.ui.MainActivity
import com.anuj.pockotlin.util.Constants
import com.anuj.pockotlin.util.Utility
import com.anuj.pockotlin.viewmodel.MainScreenViewModel
import com.anuj.pockotlin.viewmodel.factory.MainScreenViewModelFactory

class MainScreenFragment : Fragment {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainScreenViewModel

    private var cb: IdlingCallback? = null

    constructor(callback: IdlingCallback) {
        cb = callback
    }

    interface IdlingCallback {
        fun onIdle()
    }

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MainScreenViewModelFactory()
        viewModel = ViewModelProviders.of(activity!!, factory).get(MainScreenViewModel::class.java)
        observeFields()
    }

    private fun observeFields() {
        viewModel.getResult().observe(this, Observer<BaseResult<Response>> { it ->
            cb?.onIdle()
            if (viewModel.isSuccessful(it.response)) {
                removeErrorMessage()
                (binding.rvItemList.adapter as MainScreenListAdapter).setListItems(it.response?.rows)
                setScreenTitle(it.response?.title)
            } else {
                setErrorMessage(viewModel.errorMessage)
            }
            binding.srlSwipeRefresh.isRefreshing = false
        })
    }

    private fun setScreenTitle(title: String?) {
        if (activity != null && activity is MainActivity)
            (activity as MainActivity).setScreenTitle(title)
    }

    private fun setErrorMessage(message: String) {
        binding.rvItemList.visibility = View.GONE
        binding.tvErrorText.text = message
        binding.tvErrorText.visibility = View.VISIBLE
    }

    private fun removeErrorMessage() {
        binding.rvItemList.visibility = View.VISIBLE
        binding.tvErrorText.visibility = View.GONE
        binding.tvErrorText.text = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResponseFromServer(false)
        binding.srlSwipeRefresh.setOnRefreshListener { getResponseFromServer(true) }
        binding.rvItemList.layoutManager = LinearLayoutManager(activity)
        binding.rvItemList.adapter = MainScreenListAdapter(LayoutInflater.from(activity), null)
    }

    private fun getResponseFromServer(force: Boolean) {
        if (Utility.isNetworkAvailable(activity!!)) {
            binding.srlSwipeRefresh.isRefreshing = true
            viewModel.getResponse(force)
        } else {
            cb?.onIdle()
            binding.srlSwipeRefresh.isRefreshing = false
            setErrorMessage(Constants.INTERNET_CONNECTIVITY_MESSAGE)
        }
    }

    companion object {
        fun newInstance(args: Bundle?): MainScreenFragment {
            val instance = MainScreenFragment()
            if (args != null)
                instance.arguments = args
            return instance
        }
    }
}
