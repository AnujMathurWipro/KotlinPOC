package com.anuj.pockotlin.repository

import androidx.lifecycle.MutableLiveData

import com.anuj.pockotlin.log.Logger
import com.anuj.pockotlin.mainscreen.FetchListService
import com.anuj.pockotlin.models.BaseResult
import com.anuj.pockotlin.models.Response
import com.anuj.pockotlin.util.Constants

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    fun getMainScreenList(liveData: MutableLiveData<BaseResult<Response>>) {
        val retrofit = createRetrofit(Constants.API_BASE_URL)
        val service = retrofit.create(FetchListService::class.java)
        val call = service.list
        getResponseFromServer(call, liveData)
    }

    private fun <T> getResponseFromServer(call: Call<T>,
                                          liveData: MutableLiveData<BaseResult<T>>) {
        val url = call.request().url().toString()
        Logger.debugLog("Url = $url")
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                Logger.debugLog("API Response----\n$response")
                handleResponse(response, liveData)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val response = retrofit2.Response.error<T>(400, ResponseBody.create(MediaType.get("application/json"), ""))
                handleResponse(response, liveData)
            }
        })
    }

    private fun <T> handleResponse(result: retrofit2.Response<T>,
                                   liveData: MutableLiveData<BaseResult<T>>) {

        val value = BaseResult<T>()
        if (result.isSuccessful) {
            val res = result.body()
            value.response = res
        }
        liveData.postValue(value)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}
