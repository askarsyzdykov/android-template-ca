package com.template.android_template_ca.presentation.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.template.android_template_ca.App
import com.template.android_template_ca.presentation.common.AuthKeyValueStorage
import com.template.android_template_ca.presentation.login.LoginActivity
import javax.inject.Inject

abstract class BaseFragment : MvpAppCompatFragment(), BaseMvpView {

    @Inject
    lateinit var authUtils: AuthKeyValueStorage

    open val title: String? = ""

    abstract fun getLayoutId(): Int

    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectBaseFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return if (binding != null) {
            binding?.root
        } else {
            inflater.inflate(getLayoutId(), container, false);
        }
    }

    override fun onNeedReLogin() {
        authUtils.saveToken("")
        startActivity(LoginActivity.newIntent(context!!))
    }
}