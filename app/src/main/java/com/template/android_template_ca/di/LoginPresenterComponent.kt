package com.template.android_template_ca.di

import com.template.android_template_ca.di.scopes.Presenter
import com.template.android_template_ca.presentation.login.LoginPresenter
import dagger.Component

@Presenter
@Component(dependencies = [ApplicationComponent::class])
interface LoginPresenterComponent {

    fun getLoginPresenter(): LoginPresenter

}