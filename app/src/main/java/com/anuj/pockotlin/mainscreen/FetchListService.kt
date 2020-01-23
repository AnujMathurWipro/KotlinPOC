package com.anuj.pockotlin.mainscreen

import com.anuj.pockotlin.models.Response
import com.anuj.pockotlin.util.Constants

import retrofit2.Call
import retrofit2.http.GET

interface FetchListService {
    @get:GET(Constants.API_URL)
    val list: Call<Response>
}
