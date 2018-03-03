package com.template.android_template_ca

import android.content.Context
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import com.template.android_template_ca.domain.exceptions.ExceptionFactory
import com.template.android_template_ca.domain.interactors.LoginInteractor
import com.template.android_template_ca.domain.models.Auth
import com.template.android_template_ca.domain.repositories.LoginRepository
import com.template.android_template_ca.presentation.common.AuthKeyValueStorage
import com.template.android_template_ca.presentation.common.RxSchedulersProvider
import com.template.android_template_ca.presentation.login.LoginPresenter
import com.template.android_template_ca.presentation.login.LoginView
import com.template.android_template_ca.system.ResourceManager
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class LoginPresenterTest {

    @Mock
    lateinit var loginView: LoginView

    lateinit var loginPresenter: LoginPresenter

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var authKeyValueStorage: AuthKeyValueStorage

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.single() }

        MockitoAnnotations.initMocks(this)

        val interactor = LoginInteractor(LoginTestRepository())
        loginPresenter = LoginPresenter(interactor, RxSchedulersProvider(), ResourceManager(context), authKeyValueStorage)
    }

    @Test
    @Throws(Exception::class)
    fun testCreated() {
        assertNotNull(loginPresenter)
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyEmail() {
        loginPresenter.attachView(loginView)
        `when`(context.getString(Matchers.anyInt())).thenReturn(Matchers.anyString())

        loginPresenter.login("", "qwerty")
        verify(loginView).onErrorWrongEmail(Matchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testWrongEmail() {
        loginPresenter.attachView(loginView)
        `when`(context.getString(Matchers.anyInt())).thenReturn(Matchers.anyString())

        loginPresenter.login("wrong-email", "qwerty")
        verify(loginView).onErrorWrongEmail(Matchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyPassword() {
        loginPresenter.attachView(loginView)
        `when`(context.getString(Matchers.anyInt())).thenReturn(Matchers.anyString())

        loginPresenter.login("test@email.kz", "")
        verify(loginView).onErrorEmptyPassword(Matchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testEmailAndPasswordEmpty() {
        loginPresenter.attachView(loginView)
        `when`(context.getString(Matchers.anyInt())).thenReturn(Matchers.anyString())

        loginPresenter.login("", "")
        verify(loginView).onErrorWrongEmail(Matchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testLoginFailed() {
        loginPresenter.attachView(loginView)
        `when`(context.getString(Matchers.anyInt())).thenReturn("")

        loginPresenter.login("wrong@email.kz", "wrong-password")
        TimeUnit.SECONDS.sleep(1)
        verify(loginView).showProgressDialog()
        verify(loginView).hideProgressDialog()
        verify(loginView).onError(Matchers.anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testLoginSucceeded() {
        loginPresenter.attachView(loginView)

        loginPresenter.login("test@email.kz", "qwerty")
        TimeUnit.SECONDS.sleep(1)
        verify(loginView).showProgressDialog()
        verify(loginView).hideProgressDialog()
        verify(loginView).onSuccess()
    }

    @Test
    @Throws(Exception::class)
    fun testUserAlreadyAuthorized() {
        `when`(authKeyValueStorage.isAuthorized()).thenReturn(true)
        loginPresenter.attachView(loginView)

        verify(loginView).onSuccess()
    }

    private inner class LoginTestRepository : LoginRepository {
        override fun login(email: String, password: String): Single<Auth> {
            return if ("test@email.kz" == email && "qwerty" == password) {
                Single.just(Auth("auth-token"))
            } else {
                val ex = retrofit2.HttpException(Response.error<Auth>(401, ResponseBody.create(MediaType.parse("json"), "")))
                Single.error(ExceptionFactory.getException(ex))
            }
        }
    }
}
