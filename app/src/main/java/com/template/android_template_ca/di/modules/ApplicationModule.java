package com.template.android_template_ca.di.modules;

import android.content.Context;

import com.template.android_template_ca.system.ResourceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    ResourceManager provideResourceManager(Context context) {
        return new ResourceManager(context);
    }

}
