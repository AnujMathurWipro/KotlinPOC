package com.anuj.pockotlin.viewmodel.factory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anuj.pockotlin.models.BaseResult
import com.anuj.pockotlin.models.Response

import com.anuj.pockotlin.repository.Repository
import com.anuj.pockotlin.viewmodel.MainScreenViewModel

class MainScreenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(MutableLiveData(), Repository()) as T
    }
}
