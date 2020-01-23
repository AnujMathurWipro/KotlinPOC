package com.anuj.pockotlin.log

import android.util.Log

object Logger {

    private const val TAG = "Logger"

    fun debugLog(message: String) {
        Log.d(TAG, message)
    }
}
