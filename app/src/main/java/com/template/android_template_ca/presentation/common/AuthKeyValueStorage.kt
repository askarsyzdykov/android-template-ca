package com.template.android_template_ca.presentation.common

interface AuthKeyValueStorage {

    fun getToken(): String

    fun saveToken(token: String)

    fun isAuthorized(): Boolean

    companion object {

        val TOKEN_KEY = "token"
    }

}
