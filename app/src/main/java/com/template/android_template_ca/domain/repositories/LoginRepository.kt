package com.template.android_template_ca.domain.repositories

import com.template.android_template_ca.domain.models.Auth
import io.reactivex.Single

interface LoginRepository {

    fun login(email: String, password: String): Single<Auth>

}