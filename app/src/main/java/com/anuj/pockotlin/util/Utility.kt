package com.anuj.pockotlin.util

import android.content.Context
import android.net.ConnectivityManager

import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL

object Utility {

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    fun isValidURL(url: String?): Boolean {
        if (url != null && url.length > 0) {
            try {
                val u = URL(url)
                u.toURI()
                return true
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }

        }
        return false
    }
}
