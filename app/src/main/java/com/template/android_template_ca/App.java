package com.template.android_template_ca;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.pixplicity.easyprefs.library.Prefs;
import com.template.android_template_ca.di.ApplicationComponent;
import com.template.android_template_ca.di.DaggerApplicationComponent;
import com.template.android_template_ca.di.modules.ApplicationModule;
import com.template.android_template_ca.di.modules.DataModule;

public class App extends Application {

    private static ApplicationComponent applicationComponent;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        instance = this;
        applicationComponent = buildComponent();
    }

    public ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule(BuildConfig.HOST + "/api/v1/"))
                .build();
    }

    public static Context getInstance() {
        return instance;
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
