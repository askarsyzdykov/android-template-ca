package com.template.android_template_ca.presentation.login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {

    fun showProgressDialog()

    fun hideProgressDialog()

    fun onSuccess()

    fun onError(message: String)

    fun onErrorWrongEmail(message: String)

    fun onErrorEmptyPassword(message: String)
}