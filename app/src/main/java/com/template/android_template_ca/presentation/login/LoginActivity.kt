package com.template.android_template_ca.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.template.android_template_ca.App
import com.template.android_template_ca.BuildConfig
import com.template.android_template_ca.MainActivity
import com.template.android_template_ca.R
import com.template.android_template_ca.di.DaggerLoginPresenterComponent
import com.template.android_template_ca.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : BaseActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    internal fun providePresenter(): LoginPresenter {
        val presenterComponent = DaggerLoginPresenterComponent.builder()
                .applicationComponent(App.getComponent())
                .build()

        return presenterComponent.getLoginPresenter()
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun isDisplayHomeAsUpEnabled() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            etEmail.setText("test@account.com")
            etPassword.setText("qwerty")
        }
        btnLogin.setOnClickListener { presenter.login(etEmail.text.toString(), etPassword.text.toString()) }
    }

    override fun showProgressDialog() {
        btnLogin.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressDialog() {
        btnLogin.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun onSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onErrorWrongEmail(message: String) {
        etEmail.error = message
        etEmail.requestFocus()
    }

    override fun onErrorEmptyPassword(message: String) {
        etPassword.error = message
        etPassword.requestFocus()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
