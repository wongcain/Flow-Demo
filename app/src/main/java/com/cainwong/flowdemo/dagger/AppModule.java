package com.cainwong.flowdemo.dagger;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cwong on 3/21/16.
 */
@Module
public class AppModule {

    private final Context appContext;

    public AppModule(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return appContext;
    }
    // Required to use UserID and Password to log in to system.
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    @Named("mainThread")
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named("ioThread")
    Scheduler provideNewThreadScheduler() {
        return Schedulers.io();
    }

}
