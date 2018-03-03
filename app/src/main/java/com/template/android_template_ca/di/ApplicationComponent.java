package com.template.android_template_ca.di;

import android.content.Context;

import com.template.android_template_ca.di.modules.ApplicationModule;
import com.template.android_template_ca.di.modules.DataModule;
import com.template.android_template_ca.di.modules.OkHttpInterceptorsModule;
import com.template.android_template_ca.domain.repositories.LoginRepository;
import com.template.android_template_ca.presentation.base.BaseFragment;
import com.template.android_template_ca.presentation.common.AuthKeyValueStorage;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, OkHttpInterceptorsModule.class})
public interface ApplicationComponent {

    LoginRepository provideLoginRepository();

    Context provideContext();

    AuthKeyValueStorage provideKeyValueStorage();

    void injectBaseFragment(BaseFragment fragment);
}