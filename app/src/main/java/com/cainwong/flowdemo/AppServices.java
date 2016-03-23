package com.cainwong.flowdemo;

import android.content.Context;

import com.cainwong.flowdemo.dagger.AppComponent;
import com.cainwong.flowdemo.dagger.AppModule;
import com.cainwong.flowdemo.dagger.DaggerAppComponent;

import flow.Services;
import flow.ServicesFactory;

/**
 * Created by cwong on 3/23/16.
 */
public class AppServices extends ServicesFactory {

    public static final String APP_COMPONENT = "APP_COMPONENT";

    private final AppComponent appComponent;

    AppServices(Context  context) {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
    }

    @Override
    public void bindServices(Services.Binder services) {
        services.bind(APP_COMPONENT, appComponent);
    }
}
