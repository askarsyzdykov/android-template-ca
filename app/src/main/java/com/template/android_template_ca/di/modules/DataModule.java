package com.template.android_template_ca.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.template.android_template_ca.data.LoginRepositoryImpl;
import com.template.android_template_ca.data.db.LocalDatabase;
import com.template.android_template_ca.data.network.ErrorHandler;
import com.template.android_template_ca.data.network.NetworkChecker;
import com.template.android_template_ca.data.network.RestApiService;
import com.template.android_template_ca.data.network.interceptors.NetworkCheckInterceptor;
import com.template.android_template_ca.di.qualifiers.OkHttpInterceptors;
import com.template.android_template_ca.di.qualifiers.OkHttpNetworkInterceptors;
import com.template.android_template_ca.domain.repositories.LoginRepository;
import com.template.android_template_ca.presentation.common.AuthKeyValueStorage;
import com.template.android_template_ca.presentation.common.AuthUtils;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private final String baseUrl;

    public DataModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Provides
    @Singleton
    LoginRepository provideLoginRepository(LoginRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @NonNull
    @Singleton
    OkHttpClient provideOkHttpClient(NetworkChecker networkChecker,
                                     AuthKeyValueStorage authUtils,
                                     @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                     @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkCheckInterceptor(networkChecker));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .build();
            request = request.newBuilder()
                    .addHeader("Authorization", authUtils.getToken())
                    .url(url)
                    .build();
            return chain.proceed(request);
        });

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }

    @Provides
    @Singleton
    RestApiService provideApi(Retrofit retrofit) {
        return retrofit.create(RestApiService.class);
    }

    @Provides
    @Singleton
    AuthKeyValueStorage provideKeyValueStorage() {
        return new AuthUtils();
    }

    @Provides
    @Singleton
    LocalDatabase provideLocalDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                LocalDatabase.class, "local.db`")
                .build();
    }
}
