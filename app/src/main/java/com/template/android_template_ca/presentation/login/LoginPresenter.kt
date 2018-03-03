package com.template.android_template_ca.presentation.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.template.android_template_ca.R
import com.template.android_template_ca.domain.exceptions.ExceptionFactory
import com.template.android_template_ca.domain.interactors.LoginInteractor
import com.template.android_template_ca.domain.models.Auth
import com.template.android_template_ca.presentation.common.AuthKeyValueStorage
import com.template.android_template_ca.presentation.common.RxSchedulersProvider
import com.template.android_template_ca.presentation.utils.EmailValidator
import com.template.android_template_ca.presentation.utils.ErrorMessageFactory
import com.template.android_template_ca.system.ResourceManager
import com.template.android_template_ca.system.TextUtils
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor,
                                         private val rxSchedulersProvider: RxSchedulersProvider,
                                         private val resourceManager: ResourceManager,
                                         private val authUtils: AuthKeyValueStorage)
    : MvpPresenter<LoginView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if (authUtils.isAuthorized()) {
            viewState.onSuccess()
            return
        }
    }

    fun login(email: String, password: String) {
        if (!EmailValidator.isValidEmail(email)) {
            viewState.onErrorWrongEmail(resourceManager.getString(R.string.login_error_wrong_email))
            return
        }
        if (TextUtils.isEmpty(password)) {
            viewState.onErrorEmptyPassword(resourceManager.getString(R.string.login_error_wrong_password))
            return
        }
        loginInteractor.login(email, password)
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .observeOn(rxSchedulersProvider.mainThreadScheduler)
                .doOnSubscribe { viewState.showProgressDialog() }
                .doFinally { viewState.hideProgressDialog() }
                .doOnSuccess { auth -> authUtils.saveToken(auth.authToken) }
                .subscribe(this::onSuccess, this::onError)
    }

    private fun onSuccess(auth: Auth) {
        viewState.onSuccess()
    }

    private fun onError(throwable: Throwable) {
        viewState.onError(ErrorMessageFactory.create(resourceManager, ExceptionFactory.getException(throwable)))
    }
}