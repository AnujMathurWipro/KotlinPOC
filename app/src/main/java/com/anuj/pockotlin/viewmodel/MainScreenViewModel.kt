package com.anuj.pockotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.anuj.pockotlin.models.BaseResult
import com.anuj.pockotlin.models.Response
import com.anuj.pockotlin.repository.Repository
import com.anuj.pockotlin.util.Constants

class MainScreenViewModel(private val result: MutableLiveData<BaseResult<Response>>, private val repository: Repository) : ViewModel() {

    val errorMessage: String
        get() = Constants.INTERNET_CONNECTIVITY_ERROR

    fun getResult(): LiveData<BaseResult<Response>> {
        return result
    }

    fun getResponse(force: Boolean): Boolean {
        if (force || result.value == null) {
            repository.getMainScreenList(result)
            return true
        }
        return false
    }

    fun isSuccessful(res: Response?): Boolean {
        return res?.rows?.isNullOrEmpty() == false
    }
}
