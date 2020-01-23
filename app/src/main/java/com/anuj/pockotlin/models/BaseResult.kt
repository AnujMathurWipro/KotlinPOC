package com.anuj.pockotlin.models

class BaseResult<T> {
    var errorMessage: String? = null
    var response: T? = null
}
