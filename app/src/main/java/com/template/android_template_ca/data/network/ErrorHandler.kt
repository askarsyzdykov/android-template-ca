package com.template.android_template_ca.data.network

import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class ErrorHandler @Inject constructor(private val gson: Gson) {

    fun parseError(response: Response<*>): ServerError? {
        try {
            return gson.fromJson(response.errorBody()!!.string(), ServerError::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            response.errorBody()!!.close()
        }
    }
}
