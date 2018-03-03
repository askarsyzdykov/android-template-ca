package com.template.android_template_ca.domain.interactors

import com.template.android_template_ca.domain.models.Auth
import com.template.android_template_ca.domain.repositories.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository) {

    fun login(email: String, password: String): Single<Auth> {
        return loginRepository.login(email, password)
    }

}