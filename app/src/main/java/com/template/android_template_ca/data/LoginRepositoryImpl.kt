package com.template.android_template_ca.data

import com.template.android_template_ca.data.network.RestApiService
import com.template.android_template_ca.domain.models.Auth
import com.template.android_template_ca.domain.repositories.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: RestApiService) : LoginRepository {

    override fun login(email: String, password: String): Single<Auth> {
        return apiService.login(email, password)
    }

}